#!/bin/bash

fout="./api_testing_output.txt"
echo "" > $fout





players_url="http://localhost:8080/player/all"
echo "
----------------------------------------------------------------------------------
-                         GET $players_url
----------------------------------------------------------------------------------
" >> $fout
echo "Response:" >> $fout
curl -X GET $players_url | json_pp >> $fout
echo "\n\n\n" >> $fout





quizes_url="http://localhost:8080/quiz"
echo "
----------------------------------------------------------------------------------
-                         GET $quizes_url                                        
----------------------------------------------------------------------------------
" >> $fout
echo "Response:" >> $fout
curl -X GET $quizes_url | json_pp >> $fout
echo "\n\n\n" >> $fout





quiz_details_url="http://localhost:8080/quiz/1"
echo "
----------------------------------------------------------------------------------
-                         GET $quiz_details_url
----------------------------------------------------------------------------------
" >> $fout
curl -X GET $quiz_details_url | json_pp >> $fout
echo "\n\n\n" >> $fout





quiz_categories_url="http://localhost:8080/quiz/category"
echo "
----------------------------------------------------------------------------------
-                         GET $quiz_categories_url
----------------------------------------------------------------------------------
" >> $fout
curl -X GET $quiz_categories_url | json_pp >> $fout
echo "\n\n\n" >> $fout





quiz_results_url="http://localhost:8080/quiz/1/results"
echo "
----------------------------------------------------------------------------------
-                         GET $quiz_results_url
----------------------------------------------------------------------------------
" >> $fout
echo "Response:" >> $fout
curl -X GET $quiz_results_url | json_pp >> $fout
echo "\n\n\n" >> $fout





quiz_solve_url="http://localhost:8080/quiz/solve"
   # Note - quizUuid and playerUuid values change each time the project is ran, so these values must be reset each time
quiz_uuid="a659e6d2-5383-4865-b127-5e9d828a38e9"
player_uuid="ee497788-599d-43b0-ba7b-e8d0633003d5"
quiz_solve_body="{\"quizUuid\":\"$quiz_uuid\",\"playerUuid\":\"$player_uuid\",\"answers\":[{\"questionId\":1,\"answerId\":1},{\"questionId\":2,\"answerId\":5}]}"
echo "
----------------------------------------------------------------------------------
-                         POST $quiz_solve_url
----------------------------------------------------------------------------------
" >> $fout
echo "Body:
{
  \"quizUuid\": \"$quiz_uuid\",
  \"playerUuid\": \"$player_uuid\",
  \"answers\": [
    {
      \"questionId\":1,
      \"answerId\":1
    },
    {
      \"questionId\":2,
      \"answerId\":5
    }
  ]
}
\n" >> $fout
echo "Response:" >> $fout
curl -X POST -H "Content-Type: application/json" -d $quiz_solve_body $quiz_solve_url | json_pp >> $fout
echo "\n\n\n" >> $fout





quiz_create_url="http://localhost:8080/quiz/create"
quiz_create_body="{\"name\":\"Test Your Knowledge About Marko Petrović\",\"authorId\":1,\"categoryId\":5,\"questions\":[{\"text\":
\"Which city in Croatia is Marko Petrović from?\",\"incorrectAnswers\":[\"Split\",\"Dubrovnik\",\"Rijeka\"],\"correctAnswer\":\"Zagreb\"},{\"text\":\"What is Marko Petrović's f
avorite traditional Croatian dish?\",\"incorrectAnswers\":[\"Cevapi\",\"Peka\",\"Strukli\"],\"correctAnswer\":\"Sarma\"},{\"text\":\"In which year did Marko Petrović win the Cr
oatian Sportsman of the Year award?\",\"incorrectAnswers\":[\"2015\",\"2017\",\"2021\"],\"correctAnswer\":\"2019\"},{\"text\":\"Which Croatian island is Marko Petrović's favori
te vacation spot?\",\"incorrectAnswers\":[\"Hvar\",\"Vis\",\"Korčula\"],\"correctAnswer\":\"Brač\"},{\"text\":\"What is Marko Petrović's preferred musical instrument?\",\"incor
rectAnswers\":[\"Klapa\",\"Đembe\",\"Accordion\"],\"correctAnswer\":\"Tamburica\"}]}"
echo "
----------------------------------------------------------------------------------
-                         POST $quiz_create_url
----------------------------------------------------------------------------------
" >> $fout
echo "Body:
{
  \"name\": \"Test Your Knowledge About Marko Petrović\",
  \"authorId\": 1,
  \"categoryId\": 5,
  \"questions\": [
    {
      \"text\": \"Which city in Croatia is Marko Petrović from?\",
      \"incorrectAnswers\": [
        \"Split\",
        \"Dubrovnik\",
        \"Rijeka\"
      ],
      \"correctAnswer\": \"Zagreb\"
    },
    {
      \"text\": \"What is Marko Petrović's favorite traditional Croatian dish?\",
      \"incorrectAnswers\": [
        \"Cevapi\",
        \"Peka\",
        \"Strukli\"
      ],
      \"correctAnswer\": \"Sarma\"
    },
    {
      \"text\": \"In which year did Marko Petrović win the Croatian Sportsman of the Year award?\",
      \"incorrectAnswers\": [
        \"2015\",
        \"2017\",
        \"2021\"
      ],
      \"correctAnswer\": \"2019\"
    },
    {
      \"text\": \"Which Croatian island is Marko Petrović's favorite vacation spot?\",
      \"incorrectAnswers\": [
        \"Hvar\",
        \"Vis\",
        \"Korčula\"
      ],
      \"correctAnswer\": \"Brač\"
    },
    {
      \"text\": \"What is Marko Petrović's preferred musical instrument?\",
      \"incorrectAnswers\": [
        \"Klapa\",
        \"Đembe\",
        \"Accordion\"
      ],
      \"correctAnswer\": \"Tamburica\"
    }
  ]
}
\n" >> $fout
echo "Response:
(This request has to be run manually using Postman or Insomnia)
" >> $fout
echo "\n\n\n" >> $fout





echo "\n\nOutput written to $fout"

