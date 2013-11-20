package org.netflexity.api.ssh.jsch.mq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents AMQ record.
 * 
 * @author MAX
 *
 */
public class AmqRecord {
		
		private String errorCode;     // Error code from logfile.
		private Date date;            // Date of last occurrence of the error code.
        private Date startDate;       // Date of first occurrence of the error code.
		private int count;            // The number of codes collected on interval between first and last occurrence of the code.

        public static SimpleDateFormat fUSA = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        
		/**
		 * Default constructor.
		 */
		public AmqRecord() {
		}

		/**
		 * @param errorCode
		 * @param date
		 * @throws ParseException
		 */
		public AmqRecord(String errorCode, String date) throws ParseException {
			this.errorCode = errorCode;
			setDate(date);
			count = 0;
		}

		/**
		 * @param errorCode
		 * @param date
		 */
		public AmqRecord(String errorCode, Date date) {
			this.errorCode = errorCode;
			this.date = date;
			count = 0;
		}
        
        /**
         * @return
         */
        public String getErrorCode() {
            return errorCode;
        }

		/**
		 * @param errorCode
		 */
		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode.replaceAll("\n", "");
		}

        /**
         * @return
         */
        public Date getDate() {
            return date;
        }
        
		/**
		 * @param date
		 */
		public void setDate(Date date) {
			this.date = date;
		}

		/**
         * @return the startDate
         */
        public Date getStartDate() {
            return startDate;
        }

        /**
         * @param startDate the startDate to set
         */
        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        /**
		 * @param strDate
		 * @throws ParseException
		 */
		public synchronized void setDate(String strDate) throws ParseException {
			this.date = fUSA.parse(strDate);
		}

		/**
		 * @param count
		 */
		public void setCount(int count) {
			this.count = count;
		}

		/**
		 * @return
		 */
		public int getCount() {
			return count;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return date.toString() + ": " + errorCode + "[" + count + "]";
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object obj) {
			AmqRecord object = (AmqRecord) obj;
			return errorCode.compareTo(object.errorCode) == 0 && date.equals(object.date);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			return date.hashCode() + errorCode.hashCode();
		}
	}
