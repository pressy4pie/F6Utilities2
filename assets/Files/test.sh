cd /data/data/com.pressy4pie.f6utilities2/recovery
chmod 0755 getroot
chmod 0755 busybox


./getroot

su -c mount -o remount,rw /system
su -c rm -r /system/etc/install-recovery-2.sh
su -c rm -f /system/xbin/selinuxoff
