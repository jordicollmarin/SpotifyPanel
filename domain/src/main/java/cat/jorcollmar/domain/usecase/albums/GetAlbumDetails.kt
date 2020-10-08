package cat.jorcollmar.domain.usecase.albums

import cat.jorcollmar.domain.common.BaseUseCase
import cat.jorcollmar.domain.common.SchedulersFacade
import cat.jorcollmar.domain.model.AlbumDomain
import cat.jorcollmar.domain.repository.SpotifyAlbumsRepositoryContract
import io.reactivex.Single
import javax.inject.Inject

class GetAlbumDetails @Inject constructor(
    schedulers: SchedulersFacade,
    private val spotifyAlbumsRepository: SpotifyAlbumsRepositoryContract
) : BaseUseCase.RxSingleUseCase<AlbumDomain, GetAlbumDetails.Params>(
    schedulers
) {

    override fun build(params: Params): Single<AlbumDomain> {
        return spotifyAlbumsRepository.getAlbumDetails(params.albumId)
    }

    class Params(val albumId: String)
}