#!/bin/bash

CONNECT_HOST=connect

if [[ $1 ]];then
    CONNECT_HOST=$1
fi

HEADER="Content-Type: application/json"
DATA=$( cat << EOF
{
  "name": "twitter-connect",
  "config": {
	"producer.interceptor.classes": "io.confluent.monitoring.clients.interceptor.MonitoringProducerInterceptor",
	"tasks.max": "1",
	"connector.class": "io.confluent.kafka.connect.twitter.TwitterSourceConnector",
	"twitter.oauth.consumerKey": "2yld5DBiwy4rqjGmj3VFh9KbJ",
	"twitter.debug": "true",
	"twitter.oauth.accessTokenSecret": "reaglA6PolH7Duvq86ZfwSrdIRQ93mTsA92TGot1r5hTx",
	"process.deletes": "true",
	"filter.keywords": "can2017,#can2017,GitHub,#GitHub",
	"kafka.status.topic": "twitter.raw",
	"kafka.delete.topic": "kafka-twitter-delete",
	"twitter.oauth.consumerSecret": "QeyDaG8NEplX2CvEKXv4sSGgpbzl6708XRzQEjm7yykyT9GI32",
	"twitter.oauth.accessToken": "184392563-SGa23JT9ZbvsFKD1feZCrNFpL6T0CIdZAzxe7Sgi"
  }
}
EOF
)

echo "curl -X POST -H \"${HEADER}\" --data \"${DATA}\" http://${CONNECT_HOST}:8083/connectors"
curl -X POST -H "${HEADER}" --data "${DATA}" http://${CONNECT_HOST}:8083/connectors
echo
