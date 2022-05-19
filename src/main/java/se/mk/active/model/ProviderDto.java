package se.mk.active.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public final class ProviderDto {
    @NotNull
    private String name;
    private ContactInfo contactInfo;

    public Provider toProvider() {
        return Provider.builder()
                .name(name)
                .contactInfo(contactInfo)
                .build();
    }
}
