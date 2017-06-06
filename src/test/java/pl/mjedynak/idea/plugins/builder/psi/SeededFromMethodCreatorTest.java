package pl.mjedynak.idea.plugins.builder.psi;

import com.intellij.psi.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.mjedynak.idea.plugins.builder.settings.CodeStyleSettings;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(MockitoJUnitRunner.class)
public class SeededFromMethodCreatorTest {

    @InjectMocks private SeededFromCreator seededFromCreator;
    @Mock private PsiElementFactory psiElementFactory;
    @Mock private CodeStyleSettings settings;
    @Mock private PsiClass builderClass;
    @Mock private PsiClass srcClass;
    @Mock private PsiMethod method1;
    @Mock private PsiMethod method2;
    @Mock private PsiMethod method3;
    @Mock private PsiMethod createdMethod;
    @Mock private PsiParameterList parameterList1;
    @Mock private PsiParameterList parameterList2;
    @Mock private PsiParameter parameter;

    @Before
    public void mockCodeStyleManager() {
        given(settings.getFieldNamePrefix()).willReturn("m_");
        given(settings.getParameterNamePrefix()).willReturn("p_");
        setField(seededFromCreator, "codeStyleSettings", settings);
    }

    @Test
    public void shouldCreateSeededFromMethod() {
        // given
        given(builderClass.getMethods()).willReturn((PsiMethod[]) asList(method1, method2, method3).toArray());
        given(srcClass.getName()).willReturn("RootClass");
        given(method1.getName()).willReturn("Builder");
        given(method2.getName()).willReturn("aBuilder");
        given(method2.getParameterList()).willReturn(parameterList1);
        given(parameterList1.getParametersCount()).willReturn(0);
        given(method3.getName()).willReturn("withAge");
        given(method3.getParameterList()).willReturn(parameterList2);
        given(parameterList2.getParametersCount()).willReturn(1);
        given(parameterList2.getParameters()).willReturn((PsiParameter[]) asList(parameter).toArray());
        given(parameter.getName()).willReturn("age");

        given(psiElementFactory.createMethodFromText("public Builder seededFrom(RootClass seed) { return aBuilder()" +
                                                             ".withAge(seed.age);" +
                                                             "}", srcClass)).willReturn(createdMethod);

        // when
        PsiMethod result = seededFromCreator.seededFromMethod("Builder", builderClass, srcClass);

        // then
        assertThat(result).isEqualTo(createdMethod);
    }

}