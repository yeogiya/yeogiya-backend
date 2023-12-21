package com.yeogiya.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class CommonListResponse<T> extends CommonResponse<T> {

    @Builder.Default
    private int total = 0;

    @Builder.Default
    private List<T> items = new ArrayList<>();

    public CommonListResponse(HttpStatus status) {
        super(status);
        items = new ArrayList<>();
    }

    public CommonListResponse<T> add(Collection<T> data) {
        this.total += data.size();
        this.items.addAll(data);

        return this;
    }
}
