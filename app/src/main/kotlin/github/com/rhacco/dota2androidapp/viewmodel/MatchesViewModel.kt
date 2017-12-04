package github.com.rhacco.dota2androidapp.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import android.util.Log
import github.com.rhacco.dota2androidapp.App
import github.com.rhacco.dota2androidapp.R
import github.com.rhacco.dota2androidapp.lists.Hero
import github.com.rhacco.dota2androidapp.lists.LiveMatchesItemData
import github.com.rhacco.dota2androidapp.lists.Player
import github.com.rhacco.dota2androidapp.sources.repos.CustomAPIRepository
import github.com.rhacco.dota2androidapp.sources.repos.HeroesRepository
import io.reactivex.disposables.CompositeDisposable

class MatchesViewModel(application: Application) : AndroidViewModel(application) {
    private val mDisposables = CompositeDisposable()
    val mLiveMatchesQuery = MediatorLiveData<List<LiveMatchesItemData>>()

    override fun onCleared() = mDisposables.clear()

    fun getLiveMatches() {
        mDisposables.add(CustomAPIRepository.getTopLiveMatches()
                .subscribe(
                        { result ->
                            val newLiveMatches: MutableList<LiveMatchesItemData> = mutableListOf()
                            result.forEach {
                                val newItemData = LiveMatchesItemData()
                                newItemData.mServerId = it.server_id
                                newItemData.mMatchId = it.match_id
                                newItemData.mIsTournamentMatch = it.is_tournament_match
                                if (it.is_tournament_match) {
                                    newItemData.mTeamRadiant = it.team_radiant!!
                                    newItemData.mTeamDire = it.team_dire!!
                                } else {
                                    newItemData.mTeamRadiant = App.instance.getString(R.string.team_radiant)
                                    newItemData.mTeamDire = App.instance.getString(R.string.team_dire)
                                    newItemData.mAverageMMR = it.average_mmr!!
                                }
                                newItemData.mGoldAdvantage = it.gold_advantage
                                newItemData.mRadiantScore = it.radiant_score
                                newItemData.mElapsedTime = it.elapsed_time
                                newItemData.mDireScore = it.dire_score
                                it.players.forEach {
                                    newItemData.mPlayers.add(Player(it.current_steam_name, it.official_name))
                                }
                                if (it.heroes != null)
                                    HeroesRepository.getNamesByIds(it.heroes)
                                            .forEachIndexed { index, heroName ->
                                                newItemData.mHeroes.add(Hero(heroName, it.heroes[index]))
                                            }
                                newLiveMatches.add(newItemData)
                            }
                            mLiveMatchesQuery.value = newLiveMatches
                        },
                        { error ->
                            Log.d(App.instance.getString(R.string.log_msg_debug),
                                    "Failed to fetch top live matches: " + error)
                        }
                ))
    }
}