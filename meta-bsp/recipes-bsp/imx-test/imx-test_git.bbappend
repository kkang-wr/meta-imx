# Copyright (C) 2012-2016 O.S. Systems Software LTDA.
# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2022 NXP

DEPENDS:append = " freetype"
DEPENDS:append:mx8ulp-nxp-bsp = " imx-lib"
DEPENDS:append:mx9-nxp-bsp = " imx-lib"

SRCBRANCH = "master"
IMXTEST_SRC ?= "git://github.com/nxp-imx/imx-test.git;protocol=https"
SRC_URI = "${IMXTEST_SRC};branch=${SRCBRANCH} \
           file://memtool_profile"

SRCREV = "96e2ed471885d74cf597d32e2ee59a6a116ea973"

PLATFORM:mx8ulp-nxp-bsp = "IMX8ULP"
PLATFORM:mx9-nxp-bsp = "IMX8ULP"
