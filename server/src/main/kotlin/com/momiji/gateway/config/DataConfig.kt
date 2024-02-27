package com.momiji.gateway.config

import com.momiji.gateway.repository.entity.Auditable
import java.time.LocalDateTime
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.relational.core.conversion.MutableAggregateChange
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback

@Configuration
class DataConfig {
    @Bean
    fun beforeSaveCallbackForAuditables(): BeforeSaveCallback<Auditable> {
        return BeforeSaveCallback { aggregate: Auditable, _: MutableAggregateChange<Auditable>? ->
            val now = LocalDateTime.now()

            if ((aggregate.createdAt == null)) {
                aggregate.createdAt = now
            }

            aggregate.updatedAt = now

            aggregate
        }
    }
}
