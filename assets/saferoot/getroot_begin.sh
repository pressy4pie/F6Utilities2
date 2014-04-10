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
