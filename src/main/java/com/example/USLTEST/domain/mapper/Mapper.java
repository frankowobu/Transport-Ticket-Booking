package com.example.USLTEST.domain.mapper;

public interface Mapper<A,B>{
    B mapTo(A a);

    A mapFrom(B b);

    Iterable<B> mapListTo(Iterable<A> a);




}
