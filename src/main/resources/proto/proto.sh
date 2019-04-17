#!/usr/bin/env bash
protoc -I=./ --java_out=/home/chenmq/develop/workspace/java/github/netty-test/src/main/java/ ./*.proto