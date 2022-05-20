package se.mk.active.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public final class ProviderDto {
    @NotBlank(message = "Provider name can not be empty")
    private String name;
    private ContactInfo contactInfo;

    public Provider toProvider() {
        return Provider.builder()
                .name(name)
                .contactInfo(contactInfo)
                .build();
    }
}
