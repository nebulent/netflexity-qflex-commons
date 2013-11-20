package org.netflexity.api.mq;

import java.io.Serializable;

public interface MqTopic extends Serializable {

    public String getTopicName();

    public String getTopicStatus();

    public String getTopicDesc();

}
