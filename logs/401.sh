#!/bin/bash

grep -in "java.io.IOException: Server returned HTTP response code: 401" "$1" | awk '{print $10}' | sort > 401_"$1".txt


