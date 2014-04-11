echo "I did not create saferoot. i just had to modify it for use in my app"

export PATH=/home/pressy4pie/android/adt-bundle-linux-x86_64-20131030/ndk:$PATH
ndk-build NDK_PROJECT_PATH=. APP_BUILD_SCRIPT=./Android.mk
