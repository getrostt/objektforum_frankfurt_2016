FROM jenkinsci/jenkins:2.34

USER root
RUN apt-get update -qq && \
  apt-get install -qqy apt-transport-https ca-certificates && \
  apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D && \
  echo deb https://apt.dockerproject.org/repo debian-jessie main > /etc/apt/sources.list.d/docker.list && \
  apt-get update -qq && \
  apt-get install -qqy docker-engine && \
  rm -rf /var/lib/apt/lists/* && \
  apt-get autoremove -y && \
  apt-get clean
