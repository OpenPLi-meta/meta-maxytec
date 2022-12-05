KV = "4.4.35"
SRCDATE = "20220223"

PROVIDES = "virtual/blindscan-dvbs"

require maxytec-dvb-modules.inc

SRC_URI[md5sum] = "26a3ca3937232bd6640c647e2e9a1480"
SRC_URI[sha256sum] = "61532e81995d16e242eda693269e09b332766dbe769ab68df37eafad6e9f3d66"

COMPATIBLE_MACHINE = "multiboxse"

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
