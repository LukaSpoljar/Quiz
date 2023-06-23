#!/bin/bash

fout="./api_testing_output.txt"
echo "" > $fout

quizes_url="http://localhost:8080/quiz"
echo "" >> $fout
echo "\n----------------------------------------------------------------------------------" >> $fout
echo "------------------------- GET $quizes_url -----------------------------" >> $fout
echo "----------------------------------------------------------------------------------\n" >> $fout
echo "Response:" >> $fout
curl -X GET $quizes_url | json_pp >> $fout
echo "\n\n\n" >> $fout


quiz_details_url="http://localhost:8080/quiz/1"
echo "\n----------------------------------------------------------------------------------" >> $fout
echo "------------------------- GET $quiz_details_url -----------------------------" >> $fout
echo "----------------------------------------------------------------------------------\n" >> $fout
curl -X GET $quiz_details_url | json_pp >> $fout
echo "\n\n\n" >> $fout


quiz_results_url="http://localhost:8080/quiz/1/results"
echo "\n----------------------------------------------------------------------------------" >> $fout
echo "------------------------- GET $quiz_results_url -----------------------------" >> $fout
echo "----------------------------------------------------------------------------------\n" >> $fout
echo "Response:" >> $fout
curl -X GET $quiz_results_url | json_pp >> $fout
echo "\n\n\n" >> $fout


echo "\n\nOutput written to $fout"

