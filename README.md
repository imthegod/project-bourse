# Project Bourse

This repository contains the source code of my undergraduate thesis.

## General description

Project Bourse is a system that allows stock market investors, create and configure custom technical analyses. They create the technical analyses on their mobile devices, and then send analysis requests to the system's server.

An analysis request is a sequence of rules defined by the investor through a DSL (Domain Specific Language), which is provided by the system. Here is a basic rule example: "Stocks close price on 21-12-2012 <= 500 USD". It can be read as: "Stocks whose closing price on 21-12-2012 was less than or equal to 500 USD". "Write" rules is very easy because the mobile App’s UI helps the user and assures that all the possible rules the user enunciates are valid, avoiding grammatical and syntax errors.

The mobile app also allows the user to manage and monitor his portfolios. When an investor would like to create a portfolio, he should give it a name and then add stocks to it. At this last step he must define stop loss and take profit limits for every stock. Every X minutes, the mobile app connects to internet and retrieves the current prices of the stocks that compounds the investor’s portfolios. Finally the app compares the current prices with the limits defined for each stock, if a limit is exceeded then notifies the user and allows him to pick one of the following operations: buy, hold, sell, do nothing.

The system's server has the historical data of about 1040 stocks from NYSE and NASDAQ. These data is updated every day when the stock market closes. With this stored data, the server is able to process the analyses defined by the investors and generate a result for each analysis request. The result of an analysis request is a set that contains the stocks which fulfill all the rules that compound that analysis.

Due to it is possible to build complex analysis requests, the server processes all of them asynchronously and stores their results into the DB. When an investor wants to know the results of one analysis created by him, he should retrieve the results from the system's server. When the user gets the results, he is able to pick one of the following operations: buy, hold, sell, do nothing, for each stock into the results set.

## Source code

The source code is organized into 6 different Java projects. Below you will find the general description of each project.

### DSLAndRulesSetUp

This project contains the source code necessary in order to load the system’s DSL and the DSL’s Valid Rules.

### FirstStateLoader

This project contains the source code with the logic necessary to load the base stocks which with the system will work with. Additionally the source code of this project, contains the algorithms that download the historical data for each base stock from internet, and store those records into the DB.

### ProjectBourseCommons

This project contains logic needed and used by both the server and the mobile clients of the system, such as String validators, constants, rule verifiers, etc.

### ProjectBourseMobile

This project contains the source code of the Android application. The application is designed for 10.1 inches screen Android tablets. In the current version, the mobile app's UI doesn’t adapt to different screens sizes.

### ProjectBourseEJB

This project contains the source code that represents the application architecture layer. This layer contains the EJBs which call the business logic from the domain layer.

### ProjectBourseWeb

This project contains the source code of the system’s REST services. There are three groups of services: Analysis services, DSL services and Valid Rules services.
