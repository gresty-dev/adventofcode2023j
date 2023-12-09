#!/bin/sh
DAY=$1
FORMATTED_DAY=`printf "%02d" $1`
OUTPUT="src/main/resources/dev/gresty/aoc2023/$FORMATTED_DAY"
INPUT="https://adventofcode.com/2023/day/$DAY/input"
doppler run --command="curl -o $OUTPUT --cookie session=\$AOC_SESSION $INPUT"