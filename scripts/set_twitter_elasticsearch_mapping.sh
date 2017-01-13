#!/bin/bash

HEADER="Content-Type: application/json"
DATA=$( cat << EOF
{
    "settings": {
        "number_of_shards": 1
    },
    "mappings": {
        "twitchange": {
            "properties": {
                "CreatedAt": {
                    "type": "date"
                },
                "Text": {
                    "type": "text"
                },
                "Username": {
                    "type": "text"
                },
                "Location": {
                    "type": "keyword"
                },
                "TimeZone": {
                    "type": "keyword"
                },
                "Lang": {
                    "type": "keyword"
                },
                "FollowersCount": {
                    "type": "integer"
                }
            }
        }
    }
}
EOF
)
echo submiting ...
curl -XPUT -H "${HEADER}" --data "${DATA}" 'http://elasticsearch:9200/twitter.parsed?pretty'
echo
