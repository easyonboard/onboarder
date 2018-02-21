FROM node

ARG http_proxy

RUN mkdir -p /opt/client-ui/src
WORKDIR /opt/client-ui

ADD package.json /opt/client-ui/
ADD .angular-cli.json /opt/client-ui/
ADD package-lock.json /opt/client-ui/
ADD tslint.json /opt/client-ui/
ADD tsconfig.json /opt/client-ui/

ADD src/ /opt/client-ui/src

RUN npm config set proxy ${http_proxy}
RUN npm install
EXPOSE 4200

CMD ["npm", "start"]
