package org.netflexity.api.mq.ibm;

import org.netflexity.api.mq.MqTopic;
import org.netflexity.api.mq.ibm.enums.MqTopicAttributeEnum;

public class IBMMqTopic extends AbstractIBMMqObject implements MqTopic {

    @Override
    public String getTopicName() {
        return getAttributeAsString(MqTopicAttributeEnum.TOPIC_NAME);
    }

    @Override
    public String getTopicStatus() {
        return getAttributeAsString(MqTopicAttributeEnum.TOPIC_STATUS);
    }

    @Override
    public String getTopicDesc() {
        return getAttributeAsString(MqTopicAttributeEnum.TOPIC_DESC);
    }
}
