#!/system/bin/sh

tmpdir=/data/data/com.pressy4pie.f6utilities2/saferoot/tmp
rootdir=/data/data/com.pressy4pie.f6utilities2/saferoot


mkdir $tmpdir

cp $rootdir/getroot $tmpdir
cp $rootdir/su $tmpdir
cp $rootdir/busybox $tmpdir
cp $rootdir/install-recovery.sh $tmpdir

chmod 0755 $tmpdir/getroot
chmod 0755 $tmpdir/busybox

cd $tmpdir 

./getroot
SU="/system/xbin/su"

$SU -c pm disable com.sec.knox.seandroid
$SU -c mount -o remount,rw /system
$SU -c rm /system/etc/install-recovery-2.sh
$SU -c rm /system/xbin/selinuxoff

$SU -c chmod 6755 /system/xbin/su
$SU -c chmod 6755 /system/xbin/daemonsu

su -c /system/xbin/busybox --install -s /system/xbin
su -c mount -o remount,ro /system
