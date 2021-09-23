#!/bin/sh

echo "Ydm186919" | sudo -S docker stop cvehub
sudo docker rm cvehub
sudo docker build -t cvehub:v1
sudo docker run --name cvehub -p 8080:8080 cvehub:v1

