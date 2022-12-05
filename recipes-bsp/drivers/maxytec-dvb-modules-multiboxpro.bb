KV = "4.4.35"
SRCDATE = "20221201"

PROVIDES = "virtual/blindscan-dvbs"

require maxytec-dvb-modules.inc

SRC_URI[md5sum] = "7f48da6617d72de8e6f5c12f32d6538a"
SRC_URI[sha256sum] = "18bb8d5481e88e10b14a11f433fc16c7f1084e25ba3dfe4dadf03b22c0569adb"

COMPATIBLE_MACHINE = "multiboxpro"

INITSCRIPT_NAME = "suspend"
INITSCRIPT_PARAMS = "start 89 0 ."
inherit update-rc.d

do_configure[noexec] = "1"

# Generate a simplistic standard init script
do_compile_append () {
	cat > suspend << EOF
#!/bin/sh

runlevel=runlevel | cut -d' ' -f2

if [ "\$runlevel" != "0" ] ; then
	exit 0
fi

mount -t sysfs sys /sys

/usr/bin/turnoff_power
EOF
}

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${bindir}
	install -m 0755 ${S}/suspend ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/turnoff_power ${D}${bindir}
}

do_package_qa() {
}

FILES_${PN} += " ${bindir} ${sysconfdir}/init.d"

INSANE_SKIP_${PN} += "already-stripped"
