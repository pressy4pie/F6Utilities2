#!/system/bin/sh
SU="/system/xbin/su"

echo "cleaning up"

$SU -c pm disable com.sec.knox.seandroid
$SU -c mount -o remount,rw /system
$SU -c rm /system/etc/install-recovery-2.sh
$SU -c rm /system/xbin/selinuxoff

$SU -c chmod 6755 /system/xbin/su
$SU -c chmod 6755 /system/xbin/daemonsu

su -c /system/xbin/busybox --install -s /system/xbin
su -c mount -o remount,ro /system
su -c reboot
