package github.com.rhacco.dota2androidapp.lists

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import github.com.rhacco.dota2androidapp.App
import github.com.rhacco.dota2androidapp.R
import github.com.rhacco.dota2androidapp.utilities.OnSwipeListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_live_matches.*

class LiveMatchesAdapter(private val mContext: Context) : RecyclerView.Adapter<LiveMatchesViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mItemsData: List<LiveMatchesItemData> = listOf()

    fun update(newLiveMatches: List<LiveMatchesItemData>) {
        mItemsData.forEach { oldLiveMatch ->
            newLiveMatches.forEach { newLiveMatch ->
                if (newLiveMatch.mMatchId == oldLiveMatch.mMatchId) {
                    newLiveMatch.mShowAdditionalInfo = oldLiveMatch.mShowAdditionalInfo
                    newLiveMatch.mShowOfficialNames = oldLiveMatch.mShowOfficialNames
                }
            }
        }
        mItemsData = newLiveMatches
        notifyItemRangeChanged(0, mItemsData.size)
    }

    fun switchShowAdditionalInfo(itemPosition: Int) {
        mItemsData[itemPosition].mShowAdditionalInfo = !mItemsData[itemPosition].mShowAdditionalInfo
        notifyItemChanged(itemPosition)
    }

    fun switchShowOfficialNames(itemPosition: Int) {
        mItemsData[itemPosition].mShowOfficialNames = !mItemsData[itemPosition].mShowOfficialNames
        notifyItemChanged(itemPosition)
    }

    override fun getItemCount(): Int = mItemsData.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LiveMatchesViewHolder =
            LiveMatchesViewHolder(
                    mContext, mInflater.inflate(R.layout.item_live_matches, parent, false), this)

    override fun onBindViewHolder(holder: LiveMatchesViewHolder, position: Int) {
        val itemData = mItemsData[position]
        bindRealtimeStats(holder, itemData)
        holder.team_radiant?.text = itemData.mTeamRadiant
        holder.team_dire?.text = itemData.mTeamDire
        if (itemData.mIsTournamentMatch)
            holder.average_mmr?.visibility = View.GONE
        else {
            holder.average_mmr?.text = App.instance.getString(R.string.average_mmr, itemData.mAverageMMR)
            holder.average_mmr?.visibility = View.VISIBLE
        }
        bindPlayerName(holder.radiant_player0, itemData.mPlayers[0], itemData)
        bindPlayerName(holder.radiant_player1, itemData.mPlayers[1], itemData)
        bindPlayerName(holder.radiant_player2, itemData.mPlayers[2], itemData)
        bindPlayerName(holder.radiant_player3, itemData.mPlayers[3], itemData)
        bindPlayerName(holder.radiant_player4, itemData.mPlayers[4], itemData)
        bindPlayerName(holder.dire_player0, itemData.mPlayers[5], itemData)
        bindPlayerName(holder.dire_player1, itemData.mPlayers[6], itemData)
        bindPlayerName(holder.dire_player2, itemData.mPlayers[7], itemData)
        bindPlayerName(holder.dire_player3, itemData.mPlayers[8], itemData)
        bindPlayerName(holder.dire_player4, itemData.mPlayers[9], itemData)
        if (itemData.mShowAdditionalInfo && itemData.mHeroes.size == 10) {
            bindHeroName(holder.radiant_player0_hero_name, itemData.mHeroes[0])
            bindHeroPortrait(holder.radiant_player0_hero_portrait, itemData.mHeroes[0])
            bindHeroName(holder.radiant_player1_hero_name, itemData.mHeroes[1])
            bindHeroPortrait(holder.radiant_player1_hero_portrait, itemData.mHeroes[1])
            bindHeroName(holder.radiant_player2_hero_name, itemData.mHeroes[2])
            bindHeroPortrait(holder.radiant_player2_hero_portrait, itemData.mHeroes[2])
            bindHeroName(holder.radiant_player3_hero_name, itemData.mHeroes[3])
            bindHeroPortrait(holder.radiant_player3_hero_portrait, itemData.mHeroes[3])
            bindHeroName(holder.radiant_player4_hero_name, itemData.mHeroes[4])
            bindHeroPortrait(holder.radiant_player4_hero_portrait, itemData.mHeroes[4])
            bindHeroName(holder.dire_player0_hero_name, itemData.mHeroes[5])
            bindHeroPortrait(holder.dire_player0_hero_portrait, itemData.mHeroes[5])
            bindHeroName(holder.dire_player1_hero_name, itemData.mHeroes[6])
            bindHeroPortrait(holder.dire_player1_hero_portrait, itemData.mHeroes[6])
            bindHeroName(holder.dire_player2_hero_name, itemData.mHeroes[7])
            bindHeroPortrait(holder.dire_player2_hero_portrait, itemData.mHeroes[7])
            bindHeroName(holder.dire_player3_hero_name, itemData.mHeroes[8])
            bindHeroPortrait(holder.dire_player3_hero_portrait, itemData.mHeroes[8])
            bindHeroName(holder.dire_player4_hero_name, itemData.mHeroes[9])
            bindHeroPortrait(holder.dire_player4_hero_portrait, itemData.mHeroes[9])
        } else {
            holder.radiant_player0_hero_name?.visibility = View.GONE
            holder.radiant_player0_hero_portrait?.visibility = View.GONE
            holder.radiant_player1_hero_name?.visibility = View.GONE
            holder.radiant_player1_hero_portrait?.visibility = View.GONE
            holder.radiant_player2_hero_name?.visibility = View.GONE
            holder.radiant_player2_hero_portrait?.visibility = View.GONE
            holder.radiant_player3_hero_name?.visibility = View.GONE
            holder.radiant_player3_hero_portrait?.visibility = View.GONE
            holder.radiant_player4_hero_name?.visibility = View.GONE
            holder.radiant_player4_hero_portrait?.visibility = View.GONE
            holder.dire_player0_hero_name?.visibility = View.GONE
            holder.dire_player0_hero_portrait?.visibility = View.GONE
            holder.dire_player1_hero_name?.visibility = View.GONE
            holder.dire_player1_hero_portrait?.visibility = View.GONE
            holder.dire_player2_hero_name?.visibility = View.GONE
            holder.dire_player2_hero_portrait?.visibility = View.GONE
            holder.dire_player3_hero_name?.visibility = View.GONE
            holder.dire_player3_hero_portrait?.visibility = View.GONE
            holder.dire_player4_hero_name?.visibility = View.GONE
            holder.dire_player4_hero_portrait?.visibility = View.GONE
        }
        bindIds(holder, itemData)
    }

    private fun bindRealtimeStats(holder: LiveMatchesViewHolder, itemData: LiveMatchesItemData) {
        if (itemData.mHeroes.isNotEmpty()) {
            val goldAdvantageThousands = Math.floor(Math.abs(itemData.mGoldAdvantage) / 1000.0).toInt()
            val goldAdvantageHundreds = Math.round(Math.abs(itemData.mGoldAdvantage) / 100.0) % 10
            holder.gold_advantage?.text = App.instance.getString(R.string.gold_advantage,
                    goldAdvantageThousands, goldAdvantageHundreds)
            val params = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.addRule(RelativeLayout.CENTER_VERTICAL)
            params.leftMargin = 25
            params.rightMargin = 25
            if (itemData.mGoldAdvantage >= 0)
                params.addRule(RelativeLayout.LEFT_OF, R.id.score_radiant)
            else
                params.addRule(RelativeLayout.RIGHT_OF, R.id.score_dire)
            holder.gold_advantage?.layoutParams = params
            holder.gold_advantage?.visibility = View.VISIBLE
        } else
            holder.gold_advantage?.visibility = View.GONE
        holder.score_radiant?.text = itemData.mRadiantScore.toString()
        var elapsedTimeMin = 0
        var elapsedTimeSec = 0
        if (itemData.mHeroes.isNotEmpty() && itemData.mElapsedTime > 0) {
            elapsedTimeMin = Math.floor(itemData.mElapsedTime / 60.0).toInt()
            elapsedTimeSec = itemData.mElapsedTime % 60
        }
        if (elapsedTimeSec < 10)
            holder.elapsed_time?.text = App.instance.getString(R.string.elapsed_time_corrected_sec,
                    elapsedTimeMin, elapsedTimeSec)
        else
            holder.elapsed_time?.text = App.instance.getString(R.string.elapsed_time,
                    elapsedTimeMin, elapsedTimeSec)
        holder.score_dire?.text = itemData.mDireScore.toString()
    }

    private fun bindPlayerName(textView: TextView?, player: Player, itemData: LiveMatchesItemData) =
            when {
                player.officialName != null &&
                        (itemData.mIsTournamentMatch || itemData.mShowOfficialNames) -> {
                    textView?.text = player.officialName
                    if (itemData.mIsTournamentMatch)
                        textView?.setTextColor(ContextCompat.getColor(
                                App.instance.applicationContext, R.color.text_general))
                    else
                        textView?.setTextColor(ContextCompat.getColor(
                                App.instance.applicationContext, R.color.text_pro_player))
                }
                else -> {
                    textView?.text = player.currentSteamName
                    textView?.setTextColor(ContextCompat.getColor(
                            App.instance.applicationContext, R.color.text_general))
                }
            }

    private fun bindHeroName(textView: TextView?, hero: Hero) {
        textView?.text = hero.name
        textView?.visibility = View.VISIBLE
    }

    private fun bindHeroPortrait(imageView: ImageView?, hero: Hero) {
        val portraitName = "hero_portrait_vert_" + hero.id
        val portraitId = App.instance.resources.getIdentifier(
                portraitName, "drawable", App.instance.packageName)
        imageView?.setImageDrawable(ContextCompat.getDrawable(App.instance.applicationContext, portraitId))
        imageView?.visibility = View.VISIBLE
    }

    private fun bindIds(holder: LiveMatchesViewHolder, itemData: LiveMatchesItemData) =
            when {
                itemData.mShowAdditionalInfo -> {
                    holder.server_id?.text = App.instance.getString(
                            R.string.additional_info_server_id, itemData.mServerId)
                    holder.server_id?.visibility = View.VISIBLE
                    holder.match_id?.text = App.instance.getString(
                            R.string.additional_info_match_id, itemData.mMatchId)
                    holder.match_id?.visibility = View.VISIBLE
                }
                else -> {
                    holder.server_id?.visibility = View.GONE
                    holder.match_id?.visibility = View.GONE
                }
            }
}

class LiveMatchesItemData {
    var mIsTournamentMatch = false
    var mTeamRadiant = ""
    var mTeamDire = ""
    var mAverageMMR = 0
    var mGoldAdvantage = 0
    var mRadiantScore = 0
    var mElapsedTime = -1
    var mDireScore = 0
    var mPlayers: MutableList<Player> = mutableListOf()
    var mHeroes: MutableList<Hero> = mutableListOf()
    var mServerId = 0L
    var mMatchId = 0L
    var mShowAdditionalInfo = false
    var mShowOfficialNames = true
}

data class Player(var currentSteamName: String?, var officialName: String?)
data class Hero(var name: String?, var id: Int = 0)

class LiveMatchesViewHolder(context: Context, view: View?, private val mAdapter: LiveMatchesAdapter) :
        RecyclerView.ViewHolder(view), LayoutContainer, View.OnClickListener {
    override val containerView: View? = view

    init {
        itemView.setOnClickListener(this)
        itemView.setOnTouchListener(object : OnSwipeListener(context) {
            override fun onSwipeLeft() {
                mAdapter.switchShowOfficialNames(adapterPosition)
            }

            override fun onSwipeRight() {
                mAdapter.switchShowOfficialNames(adapterPosition)
            }
        })
    }

    override fun onClick(view: View?) = mAdapter.switchShowAdditionalInfo(adapterPosition)
}