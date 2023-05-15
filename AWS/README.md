# AWS

## Launch EC2 Instance
* [AWS Console](https://aws.amazon.com/console/)
* EC2 -> Instances
* AMI: Debian or Ubuntu
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
    * 点击安全 -> 编辑 -> 添加 -> 高级 -> 立即查找 -> Neng Zhou
     <!-- ![img](/Images/Permissions_too_open.png) -->
     <img src="/Images/Permissions_too_open.png" width="500" >

* Connect: 
  * Instance -> Connect -> SSH client
  * `ssh -i aws.pem admin@ec2-44-204-85-207.compute-1.amazonaws.com`

* Add password authentication:
  
  ```bash
  sudo nano /etc/ssh/sshd_config
  PasswordAuthentication no -> PasswordAuthentication yes
  Ctrl + O (save) -> Enter (confirm filename) -> Ctrl + X (exit)
  sudo service sshd restart
  sudo passwd [admin/ubuntu]
  ```

## Install

### Node.js: 

  ```bash
  sudo apt update
  sudo apt install nodejs
  node -v
  ```

### npm:

  ```bash
  sudo apt update
  sudo apt install npm
  npm -v
  ```

### git:
  
  ```bash
  sudo apt update
  sudo apt-get install git
  git --version
  ```

### Nginx: 
* 若服务器不可操作，只开放了80端口
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
    proxy_pass http://localhost:3000;
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

* 原理：
  * 监听 80 端口，如有请求跳转到 proxy_pass 对应的服务器上

### UFW - Uncomplicated Firewall:
* ubuntu 默认就是这个，无需重新安装，且不开启也能正常访问到
* debian 默认的 iptables 不好用
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
* Ubuntu 可以直接装
* 但从 Debian 9 (Stretch) 开始，Debian 官方库中的 Mysql 就被 MariaDB 替代了
* 需要从官网下载 MySQL 配置包，再进行安装，查看最新配置包↓
* [MySQL APT Repository](https://dev.mysql.com/downloads/repo/apt/)

  ```bash
  wget http://repo.mysql.com/mysql-apt-config_0.8.25-1_all.deb
  sudo dpkg -i mysql-apt-config_0.8.25-1_all.deb

  默认即可，选ok

  rm mysql-apt-config_0.8.25-1_all.deb
  sudo apt update
  sudo apt install mysql-server

  pop up default authentication plugin -> choose RECOMMENDED

  mysql --version

  sudo systemctl status mysql

  mysql -u root -p
  ```

* pwd: laphicet@2023
* connect remotely:
  * default: Standard TCP/IP over SSH
    * SSH: username + pwd
    * MySQL: username + pwd
    * 服务器不可操作，那就用这个
  * modify: Standard TCP/IP
    * modify config:

      ```bash
      sudo nano /etc/mysql/mysql.conf.d/mysqld.cnf

      add bind-address = 0.0.0.0 under [mysqld] tag

      sudo service mysql restart
      ```

    * modify MySQL:

      ```mysql
      CREATE USER 'laphi'@'localhost' IDENTIFIED BY 'laphicet@2023';
      GRANT ALL PRIVILEGES ON *.* TO 'laphi'@'localhost' WITH GRANT OPTION;
      CREATE USER 'laphi'@'%' IDENTIFIED BY 'laphicet@2023';
      GRANT ALL PRIVILEGES ON *.* TO 'laphi'@'%' WITH GRANT OPTION;
      FLUSH PRIVILEGES;
      ```
    
    * 服务器可操作，add inbound rules 3306，并且做如上修改

### Java:
* ubuntu请用如下代码，debian不适用

  ```bash
  sudo apt update
  sudo apt install openjdk-8-jdk
  java -version
  sudo update-alternatives --config java
  sudo update-alternatives --set java /usr/lib/jvm/jdk1.8.0_version/bin/java
  ```

## Issues
* Regenerate key-pair may cause AWS to fail to connect
* Using a web-based EC2 instance connection always fails
  * no such issue for ubuntu
* If missing `sudo ufw allow ssh`, you may lose your EC2 instance forever