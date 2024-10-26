KV = "4.4.35"
SRCDATE = "20221213"

require maxytec-dvb-modules.inc

SRC_URI[md5sum] = "e4eb4dce5988e91ea7238c659563d1c9"
SRC_URI[sha256sum] = "bab1dba6a9dbbf579ba510c63f55ba5ab187f937ba3bc11a6c298095b7288be5"

COMPATIBLE_MACHINE = "multiboxse"

INITSCRIPT_NAME = "suspend"
INITSCRIPT_PARAMS = "start 89 0 ."
inherit update-rc.d

do_configure[noexec] = "1"

# Generate a simplistic standard init script
do_compile:append () {
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

do_install:append() {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${bindir}
	install -m 0755 ${S}/suspend ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/turnoff_power ${D}${bindir}
}

do_package_qa() {
}

FILES:${PN} += " ${bindir} ${sysconfdir}/init.d"

INSANE_SKIP:${PN} += "already-stripped"
