# AWS: Deploy MySQL and Java Backend

- [AWS: Deploy MySQL and Java Backend](#aws-deploy-mysql-and-java-backend)
  - [Launch EC2 Instance](#launch-ec2-instance)
  - [Modify EC2:](#modify-ec2)
  - [Connect to EC2:](#connect-to-ec2)
  - [Install](#install)
    - [git:](#git)
    - [Nginx:](#nginx)
    - [UFW - Uncomplicated Firewall:](#ufw---uncomplicated-firewall)
    - [MySQL:](#mysql)
      - [Using Docker:](#using-docker)
    - [Java:](#java)
    - [Maven:](#maven)
    - [Docker:](#docker)
    - [Nodejs, npm, and Yarn](#nodejs-npm-and-yarn)
    - [Nacos](#nacos)
      - [Using Docker](#using-docker-1)
    - [Ping](#ping)
    - [Telnet](#telnet)
    - [Curl](#curl)
  - [Issues](#issues)
  - [Linux](#linux)
    - [Systemd service](#systemd-service)
    - [Commands](#commands)

## Launch EC2 Instance

* [AWS Console](https://aws.amazon.com/console/)
* EC2 -> Instances
* AMI: Debian or Ubuntu (recommend)
* Instance type: t2.micro
* Key pair (login) (optional): RSA + .pem
* Network: Default
* Storage: 30GB + gp3

## Modify EC2:

* Security Groups: Edit inbound rules
  * 其实都选 Custom TCP 就行，添加如下端口
  * HTTP 80: 0.0.0.0/0
  * HTTPS 443: 0.0.0.0/0
  * MySQL 3306: 0.0.0.0/0
* Elastic IPs: 记得定时 release

## Connect to EC2:

* Use key-pair: 需要改权限，比较麻烦
  * Instance -> Connect -> SSH client
  * `ssh -i aws.pem admin@ec2-44-204-85-207.compute-1.amazonaws.com`

  * WARNING: "Permissions for 'xxxx.pem' are too open."
    * Linux: `chmod 400 aws.pem`
    * Windows:
      * 右键 -> 属性 -> 安全 -> 高级：点击禁用继承，删除所有用户的访问权限
      * 点击安全 -> 编辑 -> 添加 -> 高级 -> 立即查找 -> Neng Zhou
       <!-- ![img](/Images/Permissions_too_open.png) -->
       <img src="/Images/Permissions_too_open.png" width="400" >

* Use password authentication (recommend):
  * **一定要`sudo su`再改密码**
  
  ```bash
  sudo nano /etc/ssh/sshd_config
  PasswordAuthentication no -> PasswordAuthentication yes
  Ctrl + O (save) -> Enter (confirm filename) -> Ctrl + X (exit)
  sudo service sshd restart
  sudo su
  sudo passwd [admin/ubuntu]
  ```

## Install

### git:
  
  ```bash
  sudo apt update
  sudo apt-get install git
  git --version
  ```

### Nginx: 

* 若服务器不可操作，只开放了80端口，通过 Nginx 反向代理
* 原理：监听 80 端口，如有请求跳转到 proxy_pass 对应的服务器上
* install: 

  ```bash
  sudo apt update
  sudo apt install nginx
  sudo systemctl start nginx
  sudo systemctl status nginx
  ```

* Configure Nginx as a reverse proxy:

  ```bash
  sudo nano /etc/nginx/sites-available/default

  location / {
    # try_files $uri $uri/;
    proxy_pass http://localhost:8080;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
  }
  ```

* Test the Nginx configuration for syntax errors:

  ``` bash
  sudo nginx -t
  ```

* Restart Nginx

  ```bash
  sudo systemctl restart nginx
  ```

* Allow access to Nginx through your firewall

  ```bash
  sudo ufw allow 'Nginx Full'
  ```

### UFW - Uncomplicated Firewall:

* ubuntu 默认就是这个，无需重新安装，经测试不开启也能正常访问到
* debian 默认的 iptables 不好用，安装ufw
* install:

  ```bash
  sudo apt update
  sudo apt install ufw
  sudo ufw allow ssh
  sudo ufw allow 3306
  sudo ufw allow 'Nginx Full'
  sudo ufw enable
  sudo ufw status
  ```

### MySQL:

* 从 Debian 9 (Stretch) 开始，Debian 官方库中的 Mysql 就被 MariaDB 替代了
* 需要从官网下载 MySQL 配置包，再进行安装，查看最新配置包↓
* [MySQL APT Repository](https://dev.mysql.com/downloads/repo/apt/)

  ```bash
  wget http://repo.mysql.com/mysql-apt-config_0.8.25-1_all.deb
  sudo dpkg -i mysql-apt-config_0.8.25-1_all.deb

  默认即可，选ok

  rm mysql-apt-config_0.8.25-1_all.deb
  ```

* Ubuntu 可以直接装

  ```bash
  sudo apt update
  sudo apt install mysql-server

  pop up default authentication plugin -> choose RECOMMENDED

  mysql --version

  sudo systemctl status mysql

  mysql -u root -p
  ```

* pwd: 
  * root@123456
  * ubuntu: 默认为空，直接进入 `sudo mysql`
* connect remotely:
  * default: Standard TCP/IP over SSH
    * SSH: username + pwd
    * MySQL: username + pwd
    * 服务器不可操作，那就用这个
  * modified: Standard TCP/IP
    * 以下操作可以通过 shell 脚本替代：[mysql_init.sh](mysql_init.sh)

      ```bash
      sudo sh mysql_init.sh
      ```

    * modify config:

      ```bash
      sudo nano /etc/mysql/mysql.conf.d/mysqld.cnf

      [mysqld]
      bind-address = 0.0.0.0

      sudo service mysql restart
      ```

    * modify MySQL:

      ```mysql
      CREATE USER 'root'@'localhost' IDENTIFIED BY 'root@123456';
      GRANT ALL PRIVILEGES ON *.* TO 'laphi'@'localhost' WITH GRANT OPTION;
      CREATE USER 'root'@'%' IDENTIFIED BY 'root@123456';
      GRANT ALL PRIVILEGES ON *.* TO 'laphi'@'%' WITH GRANT OPTION;
      FLUSH PRIVILEGES;
      ```
    
    * 服务器可操作，add inbound rules 3306，并且做如上修改
    * 简单密码时，千万不要开放 3306 端口，以免被盗

* 内存占用过大的问题：

  ```bash
  sudo nano /etc/mysql/mysql.conf.d/mysqld.cnf
  ```

  * Default: 400M
  * `performance_schema = off` -> 182.7M
  * `table_definition_cache = 400` -> 297M
  * `table_open_cache = 256` -> 330M
  * 全部修改: 164M
  * 可以只修改第一个，找不到就粘在最后一行

* 运行初始化脚本：

  ```bash
  sudo mysql -u root -p < init.sql 
  ```

#### Using Docker:

* command:

```bash
sudo docker run -d -p 3306:3306 --name mysql8 -e MYSQL_ROOT_PASSWORD=123456 -v /etc/localtime:/etc/localtime -v /etc/timezone:/etc/timezone -v [xxx/create_table.sql]:/docker-entrypoint-initdb.d/create_table.sql mysql:8
```

### Java:

* ubuntu请用如下代码，debian不适用

  ```bash
  sudo apt update
  sudo apt install openjdk-8-jdk
  java -version
  sudo update-alternatives --config java
  sudo update-alternatives --set java /usr/lib/jvm/jdk1.8.0_version/bin/java
  ```

### Maven:

```bash
sudo apt update
sudo apt install maven
mvn -version
```

### Docker:

* Install: [docker docs](https://docs.docker.com/engine/install/ubuntu/)

```bash
sudo docker build -t name:v0.0.1 .
sudo docker images
sudo docker run -p 8080:8080 name:v0.0.1

sudo docker ps -a
sudo docker rmi -f <imageid>
sudo docker rm -f <containerid>
```

* 其他命令：

  * 交互模式启动容器：

    ```bash
    sudo docker run -it name:v0.0.1 bash
    ```

  * 后台启动容器：

    ```bash
    sudo docker run -d --name [name] name:v0.0.1
    ```

  * 删除所有停止的容器:

    ```bash
    sudo docker container prune -f
    ```

  * 停止所有容器：

    ```bash
    sudo docker stop $(sudo docker ps -aq)
    ```
  
  * 删除所有容器:

    ```bash
    sudo docker rm $(sudo docker ps -aq)
    ```

  * 删除所有的镜像:

    ```bash
    sudo docker rmi $(sudo docker images -q)
    ```

  * 查看日志输出，-f 跟踪输出：

    ```bash
    sudo docker logs -f [containerId or NAME]
    ```
  
  * 不使用缓存构建容器：

    ```bash
    sudo docker build --no-cache -t name:v0.0.1 .
    ```

  * 删除中间容器：(默认构建成功就会删除)

    ```bash
    sudo docker build --force-rm=true -t name:v0.0.1 .
    ```

  * 

* Docker Compose:
  * 批量构建镜像并启动，多写一个 `docker-compose.yml`
  * https://blog.csdn.net/qq_26545503/article/details/126707380
  * https://blog.csdn.net/yanzi920403/article/details/119345898
  * 启动：

    ```bash
    sudo docker compose up -d
    ```

  * 单个启动：

    ```bash
    sudo docker compose up -d [name]
    ```

  * 进入交互式bash

    ```bash
    sudo docker compose exec <id> bash
    ```

### Nodejs, npm, and Yarn

```bash
sudo apt-get update
sudo curl -sL https://deb.nodesource.com/setup_16.x | sudo bash -
sudo apt-get install -y nodejs
sudo npm install -g yarn
node -v
npm -v
yarn -v
```

### Nacos

* Ubuntu Install:

```bash
wget https://github.com/alibaba/nacos/releases/download/2.2.3/nacos-server-2.2.3.tar.gz
tar -xvf nacos-server-2.2.3.tar.gz
rm nacos-server-2.2.3.tar.gz
```

* Start Server

```bash
bash ./nacos/bin/startup.sh -m standalone
```

* Shutdown Server

```bash
bash ./nacos/bin/shutdown.sh
```

#### Using Docker

* command:

```bash
sudo docker run -d -p 8848:8848  -p 9848:9848 -p 9849:9849 --privileged=true -e JVM_XMS=256m -e JVM_XMX=256m -e JVM_XMN=128m -e MODE=standalone nacos/nacos-server:v2.2.3
```

### Ping

```bash
apt-get update
apt-get install iputils-ping
```

### Telnet

```bash
apt-get update
apt-get install telnet
```

* Usage

```bash
telnet ip port
```

### Curl

* command

```bash
curl -v ip:port
```

## Issues

* Regenerate key-pair may cause AWS to fail to connect
* Using a web-based EC2 instance connection always fails
  * no such issue for ubuntu
* If missing `sudo ufw allow ssh`, you may lose your EC2 instance forever

## Linux

### Systemd service

* create xxx.service:

  ```bash
  cd /etc/systemd/system/
  sudo nano xxx.service
  ```

  ```
  [Unit]
  Description=Heybadminton service
  After=network.target
  StartLimitBurst=10

  [Service]
  ExecStart=/home/ubuntu/Hey_Badminton/run.sh
  Restart=always
  RestartSec=30s

  [Install]
  WantedBy=multi-user.target
  ```
* **记得给bash文件设置权限**

  ```bash
  chmod +x run.sh
  ```

* reload systemd

  ```bash
  sudo systemctl daemon-reload
  sudo systemctl start xxx
  sudo systemctl status xxx
  ```

* start automatically on system boot

  ```bash
  sudo systemctl enable xxx
  ```

  ```bash
  sudo systemctl disable xxx
  ```

* log viewer:
  * 'q': exit the viewer and return to the command line

### Commands

  * top
  * netstat:
    * -a: 显示所有选项
    * -p: 显示程序名
    * 查看特定端口：netstat -ap | grep 8080
    * Tcp类型的端口：netstat -ntlp

      ```bash
      apt-get update
      apt-get install net-tools
      ```

  * 查看特定进程：ps aux | grep nacos
  * 停止进程，进程号是前面那个：kill -9 <进程号>