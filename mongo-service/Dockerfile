# Use the official MongoDB image as the base image
FROM mongo:latest

# Set environment variables
ENV MONGO_INITDB_DATABASE analytics-dashboard

# Copy MongoDB scripts into the container
COPY ./ ./

# Expose MongoDB default port
EXPOSE 27017