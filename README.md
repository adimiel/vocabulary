# Vocabulary Email Sender

## Overview
Vocabulary Email Sender is a Java-based application that connects to OpenAI's API to generate random English vocabulary words.
The application sends these words via email every day at the same time.

This project uses the following technologies:
- **Java 17**
- **Lombok**
- **SLF4J** 
- **Docker** 

## Features
- Automatically generates English vocabulary using OpenAI's GPT API.
- Sends an email with the generated vocabulary to a recipient.
- Configurable recipient email via environment variables (`.env` file).
