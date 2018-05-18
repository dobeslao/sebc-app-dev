#!/bin/sh

DIR_REL=`dirname $0`
cd $DIR_REL
DIR=`pwd`
cd -
cd - > /dev/null

echo "Starting flume ..."

flume-ng agent --conf conf --conf-file job.config.polling --name jackson -Dflume.root.logger=INFO,console
