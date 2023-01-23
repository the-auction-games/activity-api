dapr run --app-id activityapi --app-port 8081 --dapr-http-port 3502 --components-path ./components -- java -jar ../target/*.jar
echo ""
echo "** PROGRAM CLOSED **"
