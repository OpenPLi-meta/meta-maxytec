DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES:maxytec-dvb-modules-${MACHINE} += "virtual-blindscan-dvbs"

PV = "${KV}+${SRCDATE}"

SRC_URI = "http://downloads.openpli.org/archive/maxytec/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

FILES:${PN} += "${sysconfdir}/modprobe.d/${MACHINE}.conf ${base_libdir}/modules/${KV}/extra"

do_compile() {
}

do_install() {
	install -d ${D}/lib/modules/${KV}/extra
	install -d ${D}${sysconfdir}/modprobe.d
	install -m 0755 ${S}/*.ko ${D}${base_libdir}/modules/${KV}/extra
	for i in ${MACHINE}_1 ${MACHINE}_2 ${MACHINE}_3 ${MACHINE}_4; do
		echo blacklist $i >> ${D}${sysconfdir}/modprobe.d/${MACHINE}.conf
	done
}

pkg_prerm:${PN}() {
	if [ "x$D" == "x" ]; then
		if [ -f /lib/modules/${KV}/extra/hi_play.ko ] ; then
			rm -f /lib/modules/${KV}/extra/hi_play.ko;
		fi
	fi
}

RREPLACES:${PN} = "kernel-module-${MACHINE}-1-4.4.35 kernel-module-${MACHINE}-2-4.4.35 kernel-module-${MACHINE}-3-4.4.35 kernel-module-${MACHINE}-4-4.4.35 kernel-module-hi-play-4.4.35"
RCONFLICTS:${PN} = "kernel-module-${MACHINE}-1-4.4.35 kernel-module-${MACHINE}-2-4.4.35 kernel-module-${MACHINE}-3-4.4.35 kernel-module-${MACHINE}-4-4.4.35 kernel-module-hi-play-4.4.35"
