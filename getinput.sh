#!/bin/sh
DAY=$1
FORMATTED_DAY=`printf "%02d" $1`
OUTPUT="src/main/resources/dev/gresty/aoc2023/$FORMATTED_DAY"
if [ -f $OUTPUT ]; then
  echo "Puzzle input file for day $FORMATTED_DAY already exists."
  exit
fi
INPUT="https://adventofcode.com/2023/day/$DAY/input"
USER_AGENT='github.com/gresty-dev/adventofcode2023j - contact chris@gresty.dev'
doppler run --command="curl -v -A \"$USER_AGENT\" -o $OUTPUT --cookie session=\$AOC_SESSION $INPUT"