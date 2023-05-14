# AWS

## Launch EC2 Instance
* [AWS Console](https://aws.amazon.com/console/)
* EC2 -> Instances
* AMI: Debian
* Instance type: t2.micro
* Key pair (login): RSA + .pem

## Modify EC2:
* Security Groups: Edit inbound rules
  * 其实都选 Custom TCP 就行，添加如下端口
  * HTTP 80: 0.0.0.0/0
  * HTTPS 443: 0.0.0.0/0
  * MySQL 3306: 0.0.0.0/0
* Elastic IPs: 记得定时 release

## Connect to EC2:
* WARNING: "Permissions for 'xxxx.pem' are too open."
  * Linux: `chmod 400 aws.pem`
  * Windows:
    * 右键 -> 属性 -> 安全 -> 高级：点击禁用继承，删除所有用户的访问权限
    * 点击安全 -> 添加 Administrator：赋予所有权限
     ![img](/Images/Permissions%20for%20'xxxx.pem'%20are%20too%20open.png)

* Connect: 
  * Instance -> Connect -> SSH client
  * `ssh -i aws.pem admin@ec2-3-85-86-25.compute-1.amazonaws.com`
