KV = "4.4.35"
SRCDATE = "20221213"

require maxytec-dvb-modules.inc

SRC_URI[md5sum] = "38a5e472fb1e36dd728c2a04c28db384"
SRC_URI[sha256sum] = "dd245956bc0746199f54b7da55bd032e0d2c2c145af12e9d5c4a8424dbc82738"

COMPATIBLE_MACHINE = "multiboxpro"

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
