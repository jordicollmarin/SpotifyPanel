package cat.jorcollmar.domain.usecase.albums

import cat.jorcollmar.domain.common.BaseUseCase
import cat.jorcollmar.domain.common.SchedulersFacade
import cat.jorcollmar.domain.model.PagedAlbumsDomain
import cat.jorcollmar.domain.repository.SpotifyAlbumsRepositoryContract
import io.reactivex.Observable
import javax.inject.Inject

class GetAlbums @Inject constructor(
    schedulers: SchedulersFacade,
    private val spotifyAlbumsRepository: SpotifyAlbumsRepositoryContract
) : BaseUseCase.RxObservableUseCase<PagedAlbumsDomain, GetAlbums.Params>(
    schedulers
) {

    override fun build(params: Params): Observable<PagedAlbumsDomain> {
        return spotifyAlbumsRepository.getAlbums(params.offset)
    }

    class Params(val offset: Int)
}