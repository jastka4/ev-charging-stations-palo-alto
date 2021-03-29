FROM tiangolo/uwsgi-nginx-flask:python3.8

# install dependencies
RUN pip install --upgrade pip
COPY ./requirements.txt /var/www/requirements.txt
RUN pip install -r /var/www/requirements.txt
