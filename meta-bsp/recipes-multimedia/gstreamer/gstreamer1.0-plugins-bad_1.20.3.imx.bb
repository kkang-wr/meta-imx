# This recipe is for the i.MX fork of gstreamer1.0-plugins-bad. For ease of
# maintenance, the top section is a verbatim copy of an OE-core
# recipe. The second section customizes the recipe for i.MX.

########### OE-core copy ##################
# Upstream hash: a21649109374fde44cf77de845cfb3cb6cbfb138

require recipes-multimedia/gstreamer/gstreamer1.0-plugins-common.inc
require recipes-multimedia/gstreamer/gstreamer1.0-plugins-license.inc

DESCRIPTION = "'Bad' GStreamer plugins and helper libraries "
HOMEPAGE = "https://gstreamer.freedesktop.org/"
BUGTRACKER = "https://gitlab.freedesktop.org/gstreamer/gst-plugins-bad/-/issues"

SRC_URI = "https://gstreamer.freedesktop.org/src/gst-plugins-bad/gst-plugins-bad-${PV}.tar.xz \
           file://0001-fix-maybe-uninitialized-warnings-when-compiling-with.patch \
           file://0002-avoid-including-sys-poll.h-directly.patch \
           file://0003-ensure-valid-sentinals-for-gst_structure_get-etc.patch \
           file://0004-opencv-resolve-missing-opencv-data-dir-in-yocto-buil.patch \
           file://0005-msdk-fix-includedir-path.patch \
           "
SRC_URI[sha256sum] = "09d3c2cf5911f0bc7da6bf557a55251779243d3de216b6a26cc90c445b423848"

S = "${WORKDIR}/gst-plugins-bad-${PV}"

LICENSE = "LGPL-2.1-or-later & GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

DEPENDS += "gstreamer1.0-plugins-base"

inherit gobject-introspection

PACKAGECONFIG ??= " \
    ${GSTREAMER_ORC} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'bluez', '', d)} \
    ${@bb.utils.filter('DISTRO_FEATURES', 'directfb vulkan x11', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gl', '', d)} \
    bz2 closedcaption curl dash dtls hls openssl sbc smoothstreaming \
    sndfile ttml uvch264 webp \
    ${@bb.utils.contains('TUNE_FEATURES', 'mx32', '', 'rsvg', d)} \
"

PACKAGECONFIG[aom]             = "-Daom=enabled,-Daom=disabled,aom"
PACKAGECONFIG[assrender]       = "-Dassrender=enabled,-Dassrender=disabled,libass"
PACKAGECONFIG[bluez]           = "-Dbluez=enabled,-Dbluez=disabled,bluez5"
PACKAGECONFIG[bz2]             = "-Dbz2=enabled,-Dbz2=disabled,bzip2"
PACKAGECONFIG[closedcaption]   = "-Dclosedcaption=enabled,-Dclosedcaption=disabled,pango cairo"
PACKAGECONFIG[curl]            = "-Dcurl=enabled,-Dcurl=disabled,curl"
PACKAGECONFIG[dash]            = "-Ddash=enabled,-Ddash=disabled,libxml2"
PACKAGECONFIG[dc1394]          = "-Ddc1394=enabled,-Ddc1394=disabled,libdc1394"
PACKAGECONFIG[directfb]        = "-Ddirectfb=enabled,-Ddirectfb=disabled,directfb"
PACKAGECONFIG[dtls]            = "-Ddtls=enabled,-Ddtls=disabled,openssl"
PACKAGECONFIG[faac]            = "-Dfaac=enabled,-Dfaac=disabled,faac"
PACKAGECONFIG[faad]            = "-Dfaad=enabled,-Dfaad=disabled,faad2"
PACKAGECONFIG[fluidsynth]      = "-Dfluidsynth=enabled,-Dfluidsynth=disabled,fluidsynth"
PACKAGECONFIG[hls]             = "-Dhls=enabled,-Dhls=disabled,"
# Pick atleast one crypto backend below when enabling hls
PACKAGECONFIG[nettle]          = "-Dhls-crypto=nettle,,nettle"
PACKAGECONFIG[openssl]         = "-Dhls-crypto=openssl,,openssl"
PACKAGECONFIG[gcrypt]          = "-Dhls-crypto=libgcrypt,,libgcrypt"
# the gl packageconfig enables OpenGL elements that haven't been ported
# to -base yet. They depend on the gstgl library in -base, so we do
# not add GL dependencies here, since these are taken care of in -base.
PACKAGECONFIG[gl]              = "-Dgl=enabled,-Dgl=disabled,"
PACKAGECONFIG[kms]             = "-Dkms=enabled,-Dkms=disabled,libdrm"
PACKAGECONFIG[libde265]        = "-Dlibde265=enabled,-Dlibde265=disabled,libde265"
PACKAGECONFIG[libssh2]         = "-Dcurl-ssh2=enabled,-Dcurl-ssh2=disabled,libssh2"
PACKAGECONFIG[lcms2]           = "-Dcolormanagement=enabled,-Dcolormanagement=disabled,lcms"
PACKAGECONFIG[modplug]         = "-Dmodplug=enabled,-Dmodplug=disabled,libmodplug"
PACKAGECONFIG[msdk]            = "-Dmsdk=enabled,-Dmsdk=disabled,intel-mediasdk"
PACKAGECONFIG[neon]            = "-Dneon=enabled,-Dneon=disabled,neon"
PACKAGECONFIG[openal]          = "-Dopenal=enabled,-Dopenal=disabled,openal-soft"
PACKAGECONFIG[opencv]          = "-Dopencv=enabled,-Dopencv=disabled,opencv"
PACKAGECONFIG[openh264]        = "-Dopenh264=enabled,-Dopenh264=disabled,openh264"
PACKAGECONFIG[openjpeg]        = "-Dopenjpeg=enabled,-Dopenjpeg=disabled,openjpeg"
PACKAGECONFIG[openmpt]         = "-Dopenmpt=enabled,-Dopenmpt=disabled,libopenmpt"
# the opus encoder/decoder elements are now in the -base package,
# but the opus parser remains in -bad
PACKAGECONFIG[opusparse]       = "-Dopus=enabled,-Dopus=disabled,libopus"
PACKAGECONFIG[resindvd]        = "-Dresindvd=enabled,-Dresindvd=disabled,libdvdread libdvdnav"
PACKAGECONFIG[rsvg]            = "-Drsvg=enabled,-Drsvg=disabled,librsvg"
PACKAGECONFIG[rtmp]            = "-Drtmp=enabled,-Drtmp=disabled,rtmpdump"
PACKAGECONFIG[sbc]             = "-Dsbc=enabled,-Dsbc=disabled,sbc"
PACKAGECONFIG[sctp]            = "-Dsctp=enabled,-Dsctp=disabled"
PACKAGECONFIG[smoothstreaming] = "-Dsmoothstreaming=enabled,-Dsmoothstreaming=disabled,libxml2"
PACKAGECONFIG[sndfile]         = "-Dsndfile=enabled,-Dsndfile=disabled,libsndfile1"
PACKAGECONFIG[srt]             = "-Dsrt=enabled,-Dsrt=disabled,srt"
PACKAGECONFIG[srtp]            = "-Dsrtp=enabled,-Dsrtp=disabled,libsrtp"
PACKAGECONFIG[tinyalsa]        = "-Dtinyalsa=enabled,-Dtinyalsa=disabled,tinyalsa"
PACKAGECONFIG[ttml]            = "-Dttml=enabled,-Dttml=disabled,libxml2 pango cairo"
PACKAGECONFIG[uvch264]         = "-Duvch264=enabled,-Duvch264=disabled,libusb1 libgudev"
PACKAGECONFIG[v4l2codecs]      = "-Dv4l2codecs=enabled,-Dv4l2codecs=disabled,libgudev"
PACKAGECONFIG[va]              = "-Dva=enabled,-Dva=disabled,libva"
PACKAGECONFIG[voaacenc]        = "-Dvoaacenc=enabled,-Dvoaacenc=disabled,vo-aacenc"
PACKAGECONFIG[voamrwbenc]      = "-Dvoamrwbenc=enabled,-Dvoamrwbenc=disabled,vo-amrwbenc"
PACKAGECONFIG[vulkan]          = "-Dvulkan=enabled,-Dvulkan=disabled,vulkan-loader shaderc-native"
PACKAGECONFIG[wayland]         = "-Dwayland=enabled,-Dwayland=disabled,wayland-native wayland wayland-protocols libdrm"
PACKAGECONFIG[webp]            = "-Dwebp=enabled,-Dwebp=disabled,libwebp"
PACKAGECONFIG[webrtc]          = "-Dwebrtc=enabled,-Dwebrtc=disabled,libnice"
PACKAGECONFIG[webrtcdsp]       = "-Dwebrtcdsp=enabled,-Dwebrtcdsp=disabled,webrtc-audio-processing"
PACKAGECONFIG[zbar]            = "-Dzbar=enabled,-Dzbar=disabled,zbar"
PACKAGECONFIG[x11]             = "-Dx11=enabled,-Dx11=disabled,libxcb libxkbcommon"
PACKAGECONFIG[x265]            = "-Dx265=enabled,-Dx265=disabled,x265"

GSTREAMER_GPL = "${@bb.utils.filter('PACKAGECONFIG', 'faad resindvd x265', d)}"

EXTRA_OEMESON += " \
    -Ddoc=disabled \
    -Daes=enabled \
    -Dcodecalpha=enabled \
    -Ddecklink=enabled \
    -Ddvb=enabled \
    -Dfbdev=enabled \
    -Dipcpipeline=enabled \
    -Dshm=enabled \
    -Dtranscode=enabled \
    -Dandroidmedia=disabled \
    -Dapplemedia=disabled \
    -Dasio=disabled \
    -Davtp=disabled \
    -Dbs2b=disabled \
    -Dchromaprint=disabled \
    -Dd3dvideosink=disabled \
    -Dd3d11=disabled \
    -Ddirectsound=disabled \
    -Ddts=disabled \
    -Dfdkaac=disabled \
    -Dflite=disabled \
    -Dgme=disabled \
    -Dgs=disabled \
    -Dgsm=disabled \
    -Diqa=disabled \
    -Dkate=disabled \
    -Dladspa=disabled \
    -Dldac=disabled \
    -Dlv2=disabled \
    -Dmagicleap=disabled \
    -Dmediafoundation=disabled \
    -Dmicrodns=disabled \
    -Dmpeg2enc=disabled \
    -Dmplex=disabled \
    -Dmusepack=disabled \
    -Dnvcodec=disabled \
    -Dopenexr=disabled \
    -Dopenni2=disabled \
    -Dopenaptx=disabled \
    -Dopensles=disabled \
    -Donnx=disabled \
    -Dqroverlay=disabled \
    -Dsoundtouch=disabled \
    -Dspandsp=disabled \
    -Dsvthevcenc=disabled \
    -Dteletext=disabled \
    -Dwasapi=disabled \
    -Dwasapi2=disabled \
    -Dwildmidi=disabled \
    -Dwinks=disabled \
    -Dwinscreencap=disabled \
    -Dwpe=disabled \
    -Dzxing=disabled \
"
export OPENCV_PREFIX = "${STAGING_DIR_TARGET}${prefix}"

ARM_INSTRUCTION_SET:armv4 = "arm"
ARM_INSTRUCTION_SET:armv5 = "arm"

FILES:${PN}-freeverb += "${datadir}/gstreamer-1.0/presets/GstFreeverb.prs"
FILES:${PN}-opencv += "${datadir}/gst-plugins-bad/1.0/opencv*"
FILES:${PN}-transcode += "${datadir}/gstreamer-1.0/encoding-profiles"
FILES:${PN}-voamrwbenc += "${datadir}/gstreamer-1.0/presets/GstVoAmrwbEnc.prs"

########### End of OE-core copy ###########

########### i.MX overrides ################

DEFAULT_PREFERENCE = "-1"

LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=4fbd65380cdd255951079008b364516c"

DEPENDS:append:imxgpu2d = " virtual/libg2d"

SRC_URI:remove = "https://gstreamer.freedesktop.org/src/gst-plugins-bad/gst-plugins-bad-${PV}.tar.xz \
           file://0002-avoid-including-sys-poll.h-directly.patch \
           file://0003-ensure-valid-sentinals-for-gst_structure_get-etc.patch \
           file://0005-msdk-fix-includedir-path.patch \
"
GST1.0-PLUGINS-BAD_SRC ?= "gitsm://github.com/nxp-imx/gst-plugins-bad.git;protocol=https"
SRCBRANCH = "MM_04.07.03_2301_L6.1.y"
SRC_URI:prepend = "${GST1.0-PLUGINS-BAD_SRC};branch=${SRCBRANCH} "
SRCREV = "6db7bca26b81468b647b680d7ef67b1b8b938b00"

S = "${WORKDIR}/git"

inherit use-imx-headers

PACKAGE_ARCH:imxpxp = "${MACHINE_SOCARCH}"
PACKAGE_ARCH:mx8-nxp-bsp = "${MACHINE_SOCARCH}"

PACKAGECONFIG_REMOVE ?= " \
    dtls vulkan \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', 'gl', d)} \
"
PACKAGECONFIG:remove = "${PACKAGECONFIG_REMOVE}"
PACKAGECONFIG:append:mx8-nxp-bsp = " kms tinycompress"

PACKAGECONFIG[tinycompress]    = "-Dtinycompress=enabled,-Dtinycompress=disabled,tinycompress"

# Disable introspection to fix [GstPlayer-1.0.gir] Error
# -Dintrospection=disabled
EXTRA_OEMESON += " \
    -Donnx=disabled \
    -Dc_args="${CFLAGS} -I${STAGING_INCDIR_IMX}" \
"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"

########### End of i.MX overrides #########
