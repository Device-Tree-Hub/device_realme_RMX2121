#
# Copyright (C) 2021 The LineageOS Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Inherit some common AOSP stuff
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)

# Inherit some common PixelExperience stuff
$(call inherit-product, vendor/havoc/config/common_full_phone.mk)

# Inherit from device
$(call inherit-product, device/realme/RMX2121/device.mk)

# Gapps
TARGET_GAPPS_ARCH := arm64

PRODUCT_NAME := havoc_RMX2121
PRODUCT_DEVICE := RMX2121
PRODUCT_MANUFACTURER := realme
PRODUCT_BRAND := realme
PRODUCT_MODEL := Realme X7 Pro 5G

PRODUCT_GMS_CLIENTID_BASE := android-realme

PRODUCT_BUILD_PROP_OVERRIDES += \
    PRODUCT_DEVICE=RMX2121 \
    PRODUCT_NAME=RealmeX7Pro5G \
    PRIVATE_BUILD_DESC="sys_oplus_mssi_64_cn-user 11 RP1A.200720.011 1612759478108 release-keys"

BUILD_FINGERPRINT := alps/vnd_oppo6889/oppo6889:11/RP1A.200720.011/1612759478108:user/release-keys
