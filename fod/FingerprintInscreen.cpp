/*
 * Copyright (C) 2019 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#define LOG_TAG "FingerprintInscreenService"

#include "FingerprintInscreen.h"
#include <hidl/HidlTransportSupport.h>
#include <android-base/logging.h>
#include <fstream>
#include <cmath>
#include <thread>

/* Hardcoded stuffs */
#define FP_PRESS_PATH "/sys/kernel/oppo_display/oppo_notify_fppress"
#define DIMLAYER_PATH "/sys/kernel/oppo_display/dimlayer_bl_en"
#define AOD_PATH "/sys/kernel/oppo_display/aod_light_mode_set"
#define POWER_STATUS_PATH "/sys/kernel/oppo_display/panel_pwr"
#define X_POS 445
#define Y_POS 1805
#define FP_SIZE 190
#define FP_BEGIN 1
#define FP_ENDIT 0

namespace {

template <typename T>
static void set(const std::string& path, const T& value) {
    std::ofstream file(path);
    file << value;
    LOG(INFO) << "wrote path: " << path << ", value: " << value << "\n";
}

template <typename T>
static T get(const std::string& path, const T& def) {
    std::ifstream file(path);
    T result;

    file >> result;
    LOG(INFO) << "read path: " << path << ", value: " << result << "\n";
    return file.fail() ? def : result;
}

} // anonymous namespace

namespace vendor {
namespace lineage {
namespace biometrics {
namespace fingerprint {
namespace inscreen {
namespace V1_0 {
namespace implementation {

FingerprintInscreen::FingerprintInscreen() {
}

Return<int32_t> FingerprintInscreen::getPositionX() {
    return X_POS;
}

Return<int32_t> FingerprintInscreen::getPositionY() {
    return Y_POS;
}

Return<int32_t> FingerprintInscreen::getSize() {
    return FP_SIZE;
}

Return<void> FingerprintInscreen::onStartEnroll() {
    return Void();
}

Return<void> FingerprintInscreen::onFinishEnroll() {
    return Void();
}

Return<void> FingerprintInscreen::onPress() {
    set(DIMLAYER_PATH, FP_BEGIN);
    set(FP_PRESS_PATH, FP_BEGIN);
    return Void();
}

Return<void> FingerprintInscreen::onRelease() {
    set(FP_PRESS_PATH, FP_ENDIT);
    return Void();
}

Return<void> FingerprintInscreen::onShowFODView() {
    set(DIMLAYER_PATH, FP_BEGIN);
    return Void();
}

Return<void> FingerprintInscreen::onHideFODView() {
    set(DIMLAYER_PATH, FP_ENDIT);
    return Void();
}

Return<bool> FingerprintInscreen::handleAcquired(int32_t /* acquiredInfo */, int32_t /* vendorCode */) {
    return false;
}

Return<bool> FingerprintInscreen::handleError(int32_t /* error */, int32_t /* vendorCode */) {
    return false;
}

Return<void> FingerprintInscreen::setLongPressEnabled(bool) {
    return Void();
}

Return<int32_t> FingerprintInscreen::getDimAmount(int32_t /* brightness */) {
    return 0;
}

Return<bool> FingerprintInscreen::shouldBoostBrightness() {
    return false;
}

Return<bool> FingerprintInscreen::isDozeMode() {
    return (get(POWER_STATUS_PATH, 0) == 1) || (get(POWER_STATUS_PATH, 0) == 3);
}

Return<void> FingerprintInscreen::setCallback(const sp<::vendor::lineage::biometrics::fingerprint::inscreen::V1_0::IFingerprintInscreenCallback>& callback) {
    {
        std::lock_guard<std::mutex> _lock(mCallbackLock);
        mCallback = callback;
    }
    return Void();
}

}  // namespace implementation
}  // namespace V1_0
}  // namespace inscreen
}  // namespace fingerprint
}  // namespace biometrics
}  // namespace lineage
}  // namespace vendor
