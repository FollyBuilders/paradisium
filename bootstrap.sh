#!/bin/bash

OS=$(uname)
AARCH=$(uname -m)

NATIVE_DIR="natives"
PROCESSING_DIR="../third-party/lxstudio-ide/lib/processing-4.0b 8"

mkdir ${NATIVE_DIR}

sudo ln -s ${PROCESSING_DIR}/linux-aarch64 ${NATIVE_DIR}/linux-aarch64
sudo ln -s ${PROCESSING_DIR}/linux-amd64 ${NATIVE_DIR}/linux-amd64
sudo ln -s ${PROCESSING_DIR}/linux-arm ${NATIVE_DIR}/linux-arm
sudo ln -s ${PROCESSING_DIR}/macos-aarch64 ${NATIVE_DIR}/macos-aarch64
sudo ln -s ${PROCESSING_DIR}/macos-x86_64 ${NATIVE_DIR}/macos-x86_64
sudo ln -s ${PROCESSING_DIR}/windows-amd64 ${NATIVE_DIR}/windows-amd64

# NOTE(G3): joglamp jni was looking for macosx-universal.. let's set that based on the 
# cpu architecture
if [[ "$OS" == 'Darwin' ]]; then
    if [[ "$AARCH" == 'arm64' ]]; then
        sudo ln -s ${PROCESSING_DIR}/macos-aarch64 ${NATIVE_DIR}/macosx-universal;
    else
       sudo ln -s ${PROCESSING_DIR}/macos-x86_64 ${NATIVE_DIR}/macosx-universal
    fi
fi
