package kepler.momiji.gateway.repository.entity

import java.time.LocalDateTime
import kepler.momiji.gateway.repository.entity.Auditable
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Table(
    name = "users",
    schema = "gateway",
)
data class UserEntity(
    @Id
    var id: Long? = null,
    override var createdAt: LocalDateTime? = null,
    override var updatedAt: LocalDateTime? = null,
    var username: String,
    var fullname: String,
    var frontend: String,
    var nativeId: String,
): Auditable
