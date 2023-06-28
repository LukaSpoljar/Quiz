#!/bin/bash

fout="./api_testing_output.txt"
echo "" > $fout


players_url="http://localhost:8080/player/all"
echo "" >> $fout
echo "\n----------------------------------------------------------------------------------" >> $fout
echo "------------------------- GET $players_url -----------------------------" >> $fout
echo "----------------------------------------------------------------------------------\n" >> $fout
echo "Response:" >> $fout
curl -X GET $players_url | json_pp >> $fout
echo "\n\n\n" >> $fout


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


quiz_solve_url="http://localhost:8080/quiz/solve"
# Note - quizUuid and playerUuid values change each time the project is ran, so these values must be reset each time
quiz_uuid="fef4f4e0-e6de-4de3-9399-b7d511b9a7a3"
player_uuid="b0968cb9-7787-40ac-837b-8928c63e7a78"
quiz_solve_body="{\"quizUuid\":\"$quiz_uuid\",\"playerUuid\":\"$player_uuid\",\"answers\":[{\"questionId\":1,\"answerId\":1},{\"questionId\":2,\"answerId\":5}]}"
echo "\n----------------------------------------------------------------------------------" >> $fout
echo "------------------------- POST $quiz_solve_url -----------------------------" >> $fout
echo "----------------------------------------------------------------------------------\n" >> $fout
echo "Body:\n$quiz_solve_body \n" >> $fout
echo "Response:" >> $fout
curl -X POST -H "Content-Type: application/json" -d $quiz_solve_body $quiz_solve_url | json_pp >> $fout
echo "\n\n\n" >> $fout


echo "\n\nOutput written to $fout"

