/*
 *  2007 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.ssh.jsch.mq.ibm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.text.StrTokenizer;
import org.apache.log4j.Logger;
import org.netflexity.api.ssh.SshException;
import org.netflexity.api.ssh.SshManager;
import org.netflexity.api.ssh.jsch.JschSshManager;
import org.netflexity.api.ssh.jsch.mq.AmqRecord;
import org.netflexity.api.ssh.jsch.mq.FdcFile;
import org.netflexity.api.ssh.jsch.mq.MqErrorFileManager;
import org.netflexity.api.util.StringConstants;

/**
 * @author MAX
 *
 */
public class IBMMqErrorFileManager implements MqErrorFileManager{
    
    public static final String DASH = "-";
    public static final String LOG_FILE_NAME_01 = "AMQERR01.LOG";
    public static final String LOG_FILE_NAME_02 = "AMQERR02.LOG";
    public static final String LOG_FILE_NAME_03 = "AMQERR03.LOG";

    private static Logger logger = Logger.getLogger(JschSshManager.class.getName());
    
    private final Map qmanagerFdcFileTable = Collections.synchronizedMap(new HashMap());
    private final Map qmanagerAmqErrorTable = Collections.synchronizedMap(new HashMap());
    
    /* (non-Javadoc)
     * @see org.netflexity.api.ssh.jsch.mq.ibm.MqErrorFileManager#hasNewFdcFilesAvaliableOnInterval(org.netflexity.api.ssh.SshManager, java.lang.String, long, long)
     */
    public boolean hasNewFdcFilesAvaliableOnInterval(SshManager sshManager, String qmanagerName, long startDateTime, long endDateTime) throws SshException {
        return (!getNewFdcFilesOnInterval(sshManager, qmanagerName, startDateTime, endDateTime).isEmpty());
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.ssh.jsch.mq.ibm.MqErrorFileManager#getNewFdcFilesOnInterval(org.netflexity.api.ssh.SshManager, java.lang.String, long, long)
     */
    public List getNewFdcFilesOnInterval(SshManager sshManager, String qmanagerName, long startDateTime, long endDateTime) throws SshException{
        // Change directory to qmanager' specific errors directory.
        sshManager.changeWorkingDirectory(getQmanagerErrorsDir(sshManager.getRootDir(), qmanagerName));
        
        // Check to see if new files appeared.
        List newFdcFiles = checkForNewFdcFiles(sshManager, qmanagerName);
        synchronized (qmanagerFdcFileTable) {
            for (Iterator iter = newFdcFiles.iterator(); iter.hasNext();) {
                FdcFile newFdcFile = (FdcFile) iter.next();
                long errorDateTime = newFdcFile.getAccessDate().getTime();
                boolean matchedFile = false;
                if(errorDateTime > startDateTime){
                    // If error occurred on a given interval or, if occurred later, make sure start error was before given endDateTime.
                    if(errorDateTime <= endDateTime || (newFdcFile.getCreationDate() != null && newFdcFile.getCreationDate().getTime() <= endDateTime)){
                        // Found a new file.
                        matchedFile = true;
                    }
                }
                // If new file is out of interval bounds, remove it from both collections.
                if(!matchedFile){
                    Map fdcFileTable = (Map)qmanagerFdcFileTable.get(qmanagerName);
                    synchronized (fdcFileTable) {
                        fdcFileTable.remove(newFdcFile.getFileName());
                    }
                    newFdcFiles.remove(newFdcFile);
                }
            }
        }
        
        // Remove deleted files.
        Set currentFiles = new HashSet(sshManager.getFileListing());
        synchronized (qmanagerFdcFileTable) {
            Map fdcFileTable = (Map)qmanagerFdcFileTable.get(qmanagerName);
            // Now, go through collected FDC files and find the ones that happened on the 
            // interval between startDateTime and now.
            if(fdcFileTable != null){
                synchronized (fdcFileTable) {
                    // Get a set of all currently stored files.
                    Set storedFdcFiles = new HashSet(fdcFileTable.keySet());
                    // See if any old files are remaining in our storage.
                    if(storedFdcFiles.removeAll(currentFiles)){
                        // Remove all old files.
                        for (Iterator iter = storedFdcFiles.iterator(); iter.hasNext();) {
                            String fileName = (String) iter.next();
                            fdcFileTable.remove(fileName);
                        }
                    }
                }
            }
        }
        return newFdcFiles;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.ssh.jsch.mq.ibm.MqErrorFileManager#hasErrorCodeOnInterval(org.netflexity.api.ssh.SshManager, java.lang.String, java.lang.String, long, long)
     */
    public boolean hasErrorCodeOnInterval(SshManager sshManager, String errorCode, String qmanagerName, long startDateTime, long endDateTime) throws SshException{
        return getErrorCodeOnInterval(sshManager, errorCode, qmanagerName, startDateTime, endDateTime) != null;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.ssh.jsch.mq.ibm.MqErrorFileManager#getErrorCodeOnInterval(org.netflexity.api.ssh.SshManager, java.lang.String, java.lang.String, long, long)
     */
    public AmqRecord getErrorCodeOnInterval(SshManager sshManager, String errorCode, String qmanagerName, long startDateTime, long endDateTime) throws SshException{
        // Change directory to qmanager' specific errors directory.
        sshManager.changeWorkingDirectory(getQmanagerErrorsDir(sshManager.getRootDir(), qmanagerName));
        
        // Parse AMQERR02.LOG, AMQERR01.LOG files.
        checkForNewAmqCodes(sshManager, qmanagerName);
        
        synchronized (qmanagerAmqErrorTable) {
            Map amqErrorTable = (Map)qmanagerAmqErrorTable.get(qmanagerName);
            // Now, go through collected AMQ records and find the ones that happened on the 
            // interval between startDateTime and now.
            if(amqErrorTable != null){
                synchronized (amqErrorTable) {
                    AmqRecord existingRecord = (AmqRecord)amqErrorTable.get(errorCode);
                    if(existingRecord != null){
                        long errorDateTime = existingRecord.getDate().getTime();
                        if(errorDateTime > startDateTime){
                            // If error occurred on a given interval or, if occurred later, make sure start error was before given endDateTime.
                            if(errorDateTime <= endDateTime || (existingRecord.getStartDate() != null && existingRecord.getStartDate().getTime() <= endDateTime)){
                                // Remove record if match found and it was found before. No reason, just to reset the count. 
                                /*if(existingRecord.getStartDate() != null){
                                    amqErrorTable.remove(existingRecord.getErrorCode());
                                }*/
                                
                                // Success.
                                return existingRecord;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * @param sshManager
     * @param qmanagerName
     * @return
     * @throws SshException
     */
    private List checkForNewFdcFiles(SshManager sshManager, String qmanagerName) throws SshException {
        // Retrieve ssh server's locale.
        Locale locale = Locale.getDefault(); // to avoid null pointer exception in new SimpleDateFormat
        String localeName = sshManager.executeShellCommand(JschSshManager.CMD_ECHO_LANG);
        int dotIndex = localeName.indexOf(StringConstants.DOT);
        if(dotIndex >= 0){
            localeName = localeName.substring(0, dotIndex);
        }
        Locale[] locales = Locale.getAvailableLocales();
        for (int i = 0; i < locales.length; i++) {
            Locale l = locales[i];
            if (l.toString().compareTo(localeName) == 0) {
                locale = l;
                break;
            }
        }
        
        // Share Date formats for all fdc files.
        SimpleDateFormat formatForCurrentYear = new SimpleDateFormat(FdcFile.DATE_FORMAT_PATTERN_FOR_CURRENT_YEAR, locale);
        SimpleDateFormat formatForOtherYears = new SimpleDateFormat(FdcFile.DATE_FORMAT_PATTERN_FOR_OTHER_YEARS, locale);
        
        // Find new fdc files and return them.
        ArrayList newFiles = new ArrayList();
        String[] lines = sshManager.executeShellCommand(JschSshManager.CMD_LS_FDC).split(JschSshManager.NEWLINE);
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            StrTokenizer stok = new StrTokenizer(line, StringConstants.SPACE);
            stok.setQuoteChar('\"');
            if (stok.size() < 9){
                continue;
            }
            
            // Create fdc file record.
            FdcFile fdcFile = new FdcFile();
            fdcFile.setAccess(stok.nextToken());        // Access
            fdcFile.setLinksCount(stok.nextToken());    // Links count
            fdcFile.setUser(stok.nextToken());          // User
            fdcFile.setGroup(stok.nextToken());         // Group
            fdcFile.setSize(stok.nextToken());          // Size
            String dateBuff = stok.nextToken();         // Access date
            dateBuff += StringConstants.SPACE + stok.nextToken();
            dateBuff += StringConstants.SPACE + stok.nextToken();
            try {
                fdcFile.setAccessDate(dateBuff, locale, formatForCurrentYear, formatForOtherYears);
            } 
            catch (ParseException e) {
                logger.warn(e.getMessage(), e);
                continue;
            }
            String fileName = stok.nextToken();         // File Name
            fdcFile.setFileName(fileName);
            
            // Store fileName.
            synchronized (qmanagerFdcFileTable) {
                Map fdcFileTable = (Map)qmanagerFdcFileTable.get(qmanagerName);
                if(fdcFileTable == null){
                    fdcFileTable = Collections.synchronizedMap(new HashMap());
                    qmanagerFdcFileTable.put(qmanagerName, fdcFileTable);
                }
                synchronized (fdcFileTable) {
                    FdcFile existingFdcFile = (FdcFile)fdcFileTable.get(fileName);
                    if(existingFdcFile != null){
                        if(!existingFdcFile.equals(fdcFile)){
                            if(existingFdcFile.getCreationDate() == null){
                                existingFdcFile.setCreationDate(existingFdcFile.getAccessDate());
                            }
                            existingFdcFile.setAccessDate(fdcFile.getAccessDate());
                            newFiles.add(fdcFile);
                        }
                    }
                    else{
                        fdcFileTable.put(fileName, fdcFile);
                        newFiles.add(fdcFile);
                    }
                }
            }
        }
        return newFiles;
    }
    
    /**
     * Parse AMQ file content.
     * 
     * @param sshManager
     * @param fileName name of AMQ file
     * @param qmanagerName
     * @return list of AMQ records
     * @throws SshException
     */
    private List parseAmqContent(SshManager sshManager, String fileName, String qmanagerName) throws SshException {
        String content = sshManager.getTextFileContent(fileName);

        List result = new ArrayList();
        StrTokenizer tokens = new StrTokenizer(content, DASH + JschSshManager.NEWLINE);
        Date lastErr = null;

        String prevCode = StringConstants.EMPTY;
        Date prevDate = new Date();
        int n = 1;

        for (int i = 0; i < tokens.getTokenArray().length; i++) {
            String block = tokens.getTokenArray()[i];
            AmqRecord record = new AmqRecord();
            StringTokenizer blockTokens = new StringTokenizer(block);
            String strDate = blockTokens.nextToken(DASH);
            try {
                record.setDate(strDate);
            } 
            catch (ParseException e) {
                logger.warn(e.getMessage(), e);
                continue;
            }
            blockTokens.nextToken(JschSshManager.NEWLINE);
            String strErrCode = blockTokens.nextToken(StringConstants.COLON);
            record.setErrorCode(strErrCode);
            
            if (prevCode.equals(record.getErrorCode()) && prevDate.equals(record.getDate())) {
                n++;
            } 
            else{
                n = 1;
            }

            // Get date and count of the last occurrence of this error code.
            Date amqLastErrorDate = getAmqErrorLastDate(record.getErrorCode(), qmanagerName);
            int amqLastCount = getAmqErrorCount(record.getErrorCode(), qmanagerName);
            if (amqLastErrorDate == null || record.getDate().after(amqLastErrorDate)) {
                result.add(record);
                if (lastErr == null || record.getDate().after(lastErr)) {
                    lastErr = record.getDate();
                }
            } 
            else if (amqLastErrorDate != null && record.getDate().equals(amqLastErrorDate)) {
                if (n > amqLastCount)
                    result.add(record);
            }
            prevCode = record.getErrorCode();
            prevDate = record.getDate();
        }

        return result;
    }

    /**
     * @param sshManager
     * @param qmanagerName
     * @return
     * @throws SshException
     */
    private List checkForNewAmqCodes(SshManager sshManager, String qmanagerName) throws SshException {
        List result = new ArrayList();
        List records = parseAmqContent(sshManager, LOG_FILE_NAME_02, qmanagerName);
        records.addAll(parseAmqContent(sshManager, LOG_FILE_NAME_01, qmanagerName));
        
        synchronized (qmanagerAmqErrorTable) {
            Map amqErrorTable = (Map)qmanagerAmqErrorTable.get(qmanagerName);
            if(amqErrorTable == null){
                amqErrorTable = Collections.synchronizedMap(new HashMap());
                qmanagerAmqErrorTable.put(qmanagerName, amqErrorTable);
            }
            synchronized (amqErrorTable) {
                for (Iterator iter = records.iterator(); iter.hasNext();) {
                    AmqRecord record = (AmqRecord) iter.next();
                    AmqRecord existingRecord = (AmqRecord)amqErrorTable.get(record.getErrorCode());
                    if (existingRecord == null) {
                        result.add(record.getErrorCode());
                        record.setCount(1);
                        amqErrorTable.put(record.getErrorCode(), record);
                    } 
                    else {
                        if(existingRecord.getStartDate() == null){
                            existingRecord.setStartDate(existingRecord.getDate());
                        }
                        existingRecord.setDate(record.getDate());
                        existingRecord.setCount(existingRecord.getCount() + 1);
                        amqErrorTable.put(existingRecord.getErrorCode(), existingRecord);
                        result.add(record.getErrorCode());
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * @param errorCode
     * @param qmanagerName
     * @return
     */
    protected int getAmqErrorCount(String errorCode, String qmanagerName) {
        int result = 0;
        synchronized (qmanagerAmqErrorTable) {
            Map amqErrorTable = (Map)qmanagerAmqErrorTable.get(qmanagerName);
            if(amqErrorTable != null){
                synchronized (amqErrorTable) {
                    AmqRecord record = (AmqRecord)amqErrorTable.get(errorCode);
                    if (record != null) {
                        result = record.getCount();
                    }
                }  
            }
        }
        return result;
    }

    /**
     * @param errorCode
     * @param qmanagerName
     * @return
     */
    protected Date getAmqErrorLastDate(String errorCode, String qmanagerName) {
        Date result = null;
        synchronized (qmanagerAmqErrorTable) {
            Map amqErrorTable = (Map)qmanagerAmqErrorTable.get(qmanagerName);
            if(amqErrorTable != null){
                synchronized (amqErrorTable) {
                    AmqRecord record = (AmqRecord)amqErrorTable.get(errorCode);
                    if (record != null) {
                        result = record.getDate();
                    }
                }    
            }
        }
        return result;
    }
    
    /**
     * @param rootDir
     * @param qmanagerName
     * @return path in a form of $data_dir/qmgrs/QMGRNAME/errors/
     */
    protected static String getQmanagerErrorsDir(String rootDir, String qmanagerName){
        String qmErrorDir = rootDir;
        if(!qmErrorDir.endsWith(StringConstants.FORWARD_SLASH)){
            qmErrorDir = qmErrorDir + StringConstants.FORWARD_SLASH;
        }
        qmErrorDir += "qmgrs" + StringConstants.FORWARD_SLASH + qmanagerName + StringConstants.FORWARD_SLASH + "errors";
        return qmErrorDir;
    }
}
