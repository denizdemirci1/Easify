package com.deniz.easify.ui.history

import com.deniz.easify.data.source.remote.response.History
import com.deniz.easify.data.source.remote.response.Track

/**
 * @User: deniz.demirci
 * @Date: 2020-02-21
 */

sealed class HistoryViewEvent {

    data class OpenFeaturesFragment(val track: Track) : HistoryViewEvent()

    data class OpenPlaylistsFragment(val track: Track) : HistoryViewEvent()

    data class NotifyDataChanged(val historyList: ArrayList<History>) : HistoryViewEvent()

    data class ShowError(val message: String) : HistoryViewEvent()
}
