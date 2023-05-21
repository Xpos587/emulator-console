#!/bin/bash

# Задаем переменные
USER_HOME="/home/container"
# python
PYTHON_VERSION="3.10.11"
PYTHON_URL="https://www.python.org/ftp/python/${PYTHON_VERSION}/Python-${PYTHON_VERSION}.tar.xz"

# Установка Python 3.10

# Создаем директорию для установки Python и переходим в нее
mkdir -p "${USER_HOME}/python"
cd "${USER_HOME}/python"

# Скачиваем архив Python с помощью curl
curl -O "${PYTHON_URL}"

# Распаковываем архив и переходим в директорию с исходными файлами
tar -xf "Python-${PYTHON_VERSION}.tar.xz"
cd "Python-${PYTHON_VERSION}"

# Конфигурируем и устанавливаем Python
./configure --prefix="${USER_HOME}/python/${PYTHON_VERSION}" --enable-optimizations --with-ensurepip=install
make
make install

# Добавляем путь к Python в переменную PATH
echo "export PATH=\"${USER_HOME}/python/${PYTHON_VERSION}/bin:\$PATH\"" >>"${USER_HOME}/.bashrc"

# Обновляем переменные среды
source "${USER_HOME}/.bashrc"

# Устанавливаем pip
curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py && python3 get-pip.py --user

# Удаляем архив и директорию с исходными файлами Python
cd "${USER_HOME}"
rm -rf "${USER_HOME}/python/Python-${PYTHON_VERSION}.tar.xz"
rm -rf "${USER_HOME}/python/Python-${PYTHON_VERSION}"

# Готово!
echo "Python ${PYTHON_VERSION} and pip are installed in ${USER_HOME}/python/${PYTHON_VERSION}"
echo "To install packages, use the command: python3 -m pip install PACKAGE_NAME"