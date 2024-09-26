import io.ktor.client.HttpClient
import org.example.rammultiplatform.networking.KtorClient
import org.example.rammultiplatform.networking.createHttpClient
import org.example.rammultiplatform.repository.CharacterRepository
import org.example.rammultiplatform.repository.EpisodesRepository
import org.koin.dsl.module

val appModule = module {
    single<HttpClient>{
        createHttpClient(get())
    }

    single<KtorClient> {
        KtorClient(get())
    }

    single<CharacterRepository>{
        CharacterRepository(get())
    }

    single<EpisodesRepository>{
        EpisodesRepository(get())
    }

}