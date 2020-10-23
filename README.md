# optare-talk
Optare Talk about Vert.X and RxJava

## Introduction
This is a basic but complete application example to show how to use and integrate Vert.x and RxJava.

## Running
As any other basic application, just configure your IDE or command to run the Main class. No parameters or extra configuration needed.

## Testing
Theres a locustio.py file in the src/main/resources directory designed for executing hundreds of HTTP requests, so the performance can be easily observed with charts and metrics under a high-throughput scenario.

To execute this file you need to first install the locust library with pip (or pip3):
`pip3 install locust`

Once installed, just run the following command to start using Locust:
`locust -f locustio.py`

Then open the web browser an go to `http://localhost:8089`. There you will se the Locust testing console.
