kubectl apply -f k8s
kubectl set image deployments/configserver configserver=dhananjay12/configserver:$SHA
kubectl set image deployments/contactus contactus=dhananjay12/contactus:$SHA
kubectl set image deployments/eureka eureka=dhananjay12/eureka:$SHA
kubectl set image deployments/user user=dhananjay12/user:$SHA
kubectl set image deployments/zuul user=dhananjay12/zuul:$SHA