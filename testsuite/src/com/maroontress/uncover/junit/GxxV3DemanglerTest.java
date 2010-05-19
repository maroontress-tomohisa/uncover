package com.maroontress.uncover.junit;

import com.maroontress.uncover.gxx.GxxV3Demangler;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class GxxV3DemanglerTest {
    private String s;
    private String e;
    private GxxV3Demangler d;

    static {
	GxxV3Demangler instance = new GxxV3Demangler("");
	Logger logger = Logger.getLogger(
	    instance.getClass().getPackage().getName());
	logger.addHandler(new ConsoleHandler());
	//logger.setLevel(Level.ALL);
    }

    private void test(final String mangled, final String demangled) {
	d = new GxxV3Demangler(mangled);
	assertEquals(demangled, d.getName());
    }

    @Test public void standardTest() {
	// ?
	//test("St9bad_alloc", "std::bad_alloc");
	test("_ZN1f1fE", "f::f");
	test("_Z1fv", "f");
	test("_Z1fi", "f");
	test("_Z3foo3bar", "foo");
	test("_Zrm1XS_", "operator%");
	test("_ZplR1XS0_", "operator+");
	test("_ZlsRK1XS1_", "operator<<");
	test("_ZN3FooIA4_iE3barE", "Foo<int [4]>::bar");
	test("_Z1fIiEvi", "f<int>");
	test("_Z5firstI3DuoEvS0_", "first<Duo>");
	test("_Z5firstI3DuoEvT_", "first<Duo>");
        test("_Z3fooIiFvdEiEvv", "foo<int, void ()(double), int>");
	test("_ZN1N1fE", "N::f");
	test("_ZN6System5Sound4beepEv", "System::Sound::beep");
	test("_ZN5Arena5levelE", "Arena::level");
	test("_ZN5StackIiiE5levelE", "Stack<int, int>::level");
	test("_Z1fI1XEvPVN1AIT_E1TE", "f<X>");
	test("_ZngILi42EEvN1AIXplT_Li2EEE1TE", "operator- <42>");
	test("_Z4makeI7FactoryiET_IT0_Ev", "make<Factory, int>");
	test("_Z3foo5Hello5WorldS0_S_", "foo");
	test("_Z3fooPM2ABi", "foo");
	test("_ZlsRSoRKSs", "operator<<");
	// Special Name
	//test("_ZTI7a_class", "typeinfo for a_class");
	// ?
	//test("U4_farrVKPi", "int* const volatile restrict _far");
	test("_Z3fooILi2EEvRAplT_Li1E_i", "foo<2>");
	test("_Z1fM1AKFvvE", "f");
	test("_Z3fooc", "foo");
	test("_Z2f0u8char16_t", "f0");
	test("_Z2f0Pu8char16_t", "f0");
	test("_Z2f0u8char32_t", "f0");
	test("_Z2f0Pu8char32_t", "f0");
	// ?
	//test("2CBIL_Z3foocEE", "CB<foo(char)>");
	//test("2CBIL_Z7IsEmptyEE", "CB<IsEmpty>");
	// Local Name
	//test("_ZZN1N1fEiE1p", "N::f(int)::p");
	//test("_ZZN1N1fEiEs", "N::f(int)::string literal");
	test("_Z1fPFvvEM1SFvvE", "f");
	test("_ZN1N1TIiiE2mfES0_IddE", "N::T<int, int>::mf");
	test("_ZSt5state", "std::state");
	test("_ZNSt3_In4wardE", "std::_In::ward");
	test("_Z1fKPFiiE", "f");
	test("_Z1fAszL_ZZNK1N1A1fEvE3foo_0E_i", "f");
	test("_Z1fA37_iPS_", "f");
	test("_Z1fM1AFivEPS0_", "f");
	test("_Z1fPFPA1_ivE", "f");
	test("_Z1fPKM1AFivE", "f");
	test("_Z1jM1AFivEPS1_", "j");
	test("_Z1sPA37_iPS0_", "s");
	test("_Z3fooA30_A_i", "foo");
	test("_Z3kooPA28_A30_i", "koo");
	test("_ZlsRKU3fooU4bart1XS0_", "operator<<");
	test("_ZlsRKU3fooU4bart1XS2_", "operator<<");
	test("_Z1fM1AKFivE", "f");
	test("_Z3absILi11EEvv", "abs<11>");
	test("_ZN1AIfEcvT_IiEEv", "A<float>::operator int<int>");
	test("_ZN12libcw_app_ct10add_optionIS_EEvMT_FvPKcES3_cS3_S3_", "libcw_app_ct::add_option<libcw_app_ct>");
	// Special Name
	//test("_ZGVN5libcw24_GLOBAL__N_cbll.cc0ZhUKa23compiler_bug_workaroundISt6vectorINS_13omanip_id_tctINS_5debug32memblk_types_manipulator_data_ctEEESaIS6_EEE3idsE", "guard variable for libcw::(anonymous namespace)::compiler_bug_workaround<std::vector<libcw::omanip_id_tct<libcw::debug::memblk_types_manipulator_data_ct>, std::allocator<libcw::omanip_id_tct<libcw::debug::memblk_types_manipulator_data_ct> > > >::ids");
	test("_ZN5libcw5debug13cwprint_usingINS_9_private_12GlobalObjectEEENS0_17cwprint_using_tctIT_EERKS5_MS5_KFvRSt7ostreamE", "libcw::debug::cwprint_using<libcw::_private_::GlobalObject>");
	test("_ZNKSt14priority_queueIP27timer_event_request_base_ctSt5dequeIS1_SaIS1_EE13timer_greaterE3topEv", "std::priority_queue<timer_event_request_base_ct *, std::deque<timer_event_request_base_ct *, std::allocator<timer_event_request_base_ct *>>, timer_greater>::top");
	test("_ZNKSt15_Deque_iteratorIP15memory_block_stRKS1_PS2_EeqERKS5_", "std::_Deque_iterator<memory_block_st *, memory_block_st * const &, memory_block_st * const *>::operator==");
	test("_ZNKSt17__normal_iteratorIPK6optionSt6vectorIS0_SaIS0_EEEmiERKS6_", "std::__normal_iterator<option const *, std::vector<option, std::allocator<option>>>::operator-");
	test("_ZNSbIcSt11char_traitsIcEN5libcw5debug27no_alloc_checking_allocatorEE12_S_constructIPcEES6_T_S7_RKS3_", "std::basic_string<char, std::char_traits<char>, libcw::debug::no_alloc_checking_allocator>::_S_construct<char *>");
	test("_Z1fI1APS0_PKS0_EvT_T0_T1_PA4_S3_M1CS8_", "f<A, A *, A const *>");
	test("_Z3fooiPiPS_PS0_PS1_PS2_PS3_PS4_PS5_PS6_PS7_PS8_PS9_PSA_PSB_PSC_", "foo");
	test("_ZSt1BISt1DIP1ARKS2_PS3_ES0_IS2_RS2_PS2_ES2_ET0_T_SB_SA_PT1_", "std::B<std::D<A *, A * const &, A * const *>, std::D<A *, A *&, A **>, A *>");
	test("_X11TransParseAddress", "_X11TransParseAddress");
	test("_ZNSt13_Alloc_traitsISbIcSt18string_char_traitsIcEN5libcw5debug9_private_17allocator_adaptorIcSt24__default_alloc_templateILb0ELi327664EELb1EEEENS5_IS9_S7_Lb1EEEE15_S_instancelessE", "std::_Alloc_traits<std::basic_string<char, std::string_char_traits<char>, libcw::debug::_private_::allocator_adaptor<char, std::__default_alloc_template<false, 327664>, true>>, libcw::debug::_private_::allocator_adaptor<std::basic_string<char, std::string_char_traits<char>, libcw::debug::_private_::allocator_adaptor<char, std::__default_alloc_template<false, 327664>, true>>, std::__default_alloc_template<false, 327664>, true>>::_S_instanceless");
	test("_GLOBAL__I__Z2fnv", "global constructors keyed to fn");
	test("_Z1rM1GFivEMS_KFivES_M1HFivES1_4whatIKS_E5what2IS8_ES3_", "r");
	test("_Z10hairyfunc5PFPFilEPcE", "hairyfunc5");
	test("_Z1fILi1ELc120EEv1AIXplT_cviLd810000000000000000703DAD7A370C5EEE", "f<1, (char)120>");
	test("_Z1fILi1EEv1AIXplT_cvingLf3f800000EEE", "f<1>");
	test("_ZNK11__gnu_debug16_Error_formatter14_M_format_wordImEEvPciPKcT_", "__gnu_debug::_Error_formatter::_M_format_word<unsigned long>");
	test("_ZSt18uninitialized_copyIN9__gnu_cxx17__normal_iteratorIPSt4pairISsPFbP6sqlitePPcEESt6vectorIS9_SaIS9_EEEESE_ET0_T_SG_SF_", "std::uninitialized_copy<__gnu_cxx::__normal_iterator<std::pair<std::string, bool (*)(sqlite *, char **)> *, std::vector<std::pair<std::string, bool (*)(sqlite *, char **)>, std::allocator<std::pair<std::string, bool (*)(sqlite *, char **)>>>>, __gnu_cxx::__normal_iterator<std::pair<std::string, bool (*)(sqlite *, char **)> *, std::vector<std::pair<std::string, bool (*)(sqlite *, char **)>, std::allocator<std::pair<std::string, bool (*)(sqlite *, char **)>>>>>");
	test("_Z1fP1cIPFiiEE", "f");
	test("_Z4dep9ILi3EEvP3fooIXgtT_Li2EEE", "dep9<3>");
	test("_ZStltI9file_pathSsEbRKSt4pairIT_T0_ES6_", "std::operator< <file_path, std::string>");
	test("_Z9hairyfuncM1YKFPVPFrPA2_PM1XKFKPA3_ilEPcEiE", "hairyfunc");
	test("_Z1fILin1EEvv", "f<-1>");
	test("_ZNSdD0Ev", "std::basic_iostream<char, std::char_traits<char>>::~basic_iostream");
	test("_ZNK15nsBaseHashtableI15nsUint32HashKey8nsCOMPtrI4IFooEPS2_E13EnumerateReadEPF15PLDHashOperatorRKjS4_PvES9_", "nsBaseHashtable<nsUint32HashKey, nsCOMPtr<IFoo>, IFoo *>::EnumerateRead");
	test("_ZNK1C1fIiEEPFivEv", "C::f<int>");
	// Local Name
	//test("_ZZ3BBdI3FooEvvENK3Fob3FabEv", "void BBd<Foo>()::Fob::Fab");
	//test("_ZZZ3BBdI3FooEvvENK3Fob3FabEvENK3Gob3GabEv", "void BBd<Foo>()::Fob::Fab() const::Gob::Gab");
	test("_ZNK5boost6spirit5matchI13rcs_deltatextEcvMNS0_4impl5dummyEFvvEEv", "boost::spirit::match<rcs_deltatext>::operator void (boost::spirit::impl::dummy::*)()");
	test("_Z3fooIA6_KiEvA9_KT_rVPrS4_", "foo<int const [6]>");
	test("_Z3fooIA3_iEvRKT_", "foo<int [3]>");
	test("_Z3fooIPA3_iEvRKT_", "foo<int (*)[3]>");
	test("_ZN13PatternDriver23StringScalarDeleteValueC1ERKNS_25ConflateStringScalarValueERKNS_25AbstractStringScalarValueERKNS_12TemplateEnumINS_12pdcomplementELZNS_16complement_namesEELZNS_14COMPLEMENTENUMEEEE", "PatternDriver::StringScalarDeleteValue::StringScalarDeleteValue");
	test("ALsetchannels", "ALsetchannels");
	test("_Z4makeI7FactoryiET_IT0_Ev", "make<Factory, int>");
    }

    @Test public void test0() {
	s = "_Z41__static_initialization_and_destruction_0ii";
	d = new GxxV3Demangler(s);
	e = "__static_initialization_and_destruction_0";
	test(e, d.getName());
    }

    @Test public void test1() {
	s = "_ZN22gflags_mutex_namespace5Mutex9SetIsSafeEv";
	d = new GxxV3Demangler(s);
	e = "gflags_mutex_namespace::Mutex::SetIsSafe";
	test(e, d.getName());
    }

    @Test public void test2() {
	s = "_ZNK6google35_GLOBAL__N_"
	    + "_ZN3fLS14FLAGS_flagfileE9FlagValue5EqualERKS1_";
	d = new GxxV3Demangler(s);
	e = "google::(anonymous namespace)::FlagValue::Equal";
	test(e, d.getName());
    }

    @Test public void test3() {
	s = "_GLOBAL__I__ZN3fLS14FLAGS_flagfileE";
	d = new GxxV3Demangler(s);
	e = "global constructors keyed to fLS::FLAGS_flagfile";
	test(e, d.getName());
    }

    @Test public void test4() {
	s = "_ZN22gflags_mutex_namespace5MutexD1Ev";
	d = new GxxV3Demangler(s);
	e = "gflags_mutex_namespace::Mutex::~Mutex";
	test(e, d.getName());
    }

    @Test public void test5() {
	s = "_ZStlsISt11char_traitsIcEERSt13basic_ostreamIcT_ES5_PKc";
	d = new GxxV3Demangler(s);
	e = "std::operator<< <std::char_traits<char>>";
	test(e, d.getName());
    }

    @Test public void test6() {
	s = "_ZNSt9basic_iosIcSt11char_traitsIcEE8setstateESt12_Ios_Iostate";
	d = new GxxV3Demangler(s);
	e = "std::basic_ios<char, std::char_traits<char>>::setstate";
	test(e, d.getName());
    }

    @Test public void test7() {
	s = "_ZNSs4_Rep10_M_disposeERKSaIcE";
	d = new GxxV3Demangler(s);
	e = "std::string::_Rep::_M_dispose";
	test(e, d.getName());
    }

    @Test public void test8() {
	s = "_ZNSt8_Rb_treeIPKvSt4pairIKS1_PN6google"
	    + "35_GLOBAL__N__ZN3fLS14FLAGS_flagfileE"
	    + "15CommandLineFlagEESt10_Select1stIS8_ESt4lessIS1_ESaIS8_EE"
	    + "16_M_insert_uniqueESt17_Rb_tree_iteratorIS8_ERKS8_";
	d = new GxxV3Demangler(s);
	e = "std::_Rb_tree"
	    + "<void const *, "
	    + "std::pair<void const * const, "
	    + "google::(anonymous namespace)::CommandLineFlag *>, "
	    + "std::_Select1st<std::pair<void const * const, "
	    + "google::(anonymous namespace)::CommandLineFlag *>>, "
	    + "std::less<void const *>, "
	    + "std::allocator<std::pair<void const * const, "
	    + "google::(anonymous namespace)::CommandLineFlag *>>>"
	    + "::_M_insert_unique";
	test(e, d.getName());
    }

    @Test public void test9() {
	s = "_ZNSt8_Rb_treeISsSt4pairIKSsSsESt10_Select1stIS2_E"
	    + "St4lessISsESaIS2_EE16_M_insert_uniqueE"
	    + "St17_Rb_tree_iteratorIS2_ERKS2_";
	d = new GxxV3Demangler(s);
	e = "std::_Rb_tree<"
	    + "std::string, "
	    + "std::pair<std::string const, std::string>, "
	    + "std::_Select1st<std::pair<std::string const, std::string>>, "
	    + "std::less<std::string>, "
	    + "std::allocator<std::pair<std::string const, std::string>>>"
	    + "::_M_insert_unique";
	test(e, d.getName());
    }

    @Test public void test10() {
	s = "_ZNSt8_Rb_treeIPKcSt4pairIKS1_PN6google"
	    + "35_GLOBAL__N__ZN3fLS14FLAGS_flagfileE15CommandLineFlagE"
	    + "ESt10_Select1stIS8_ENS5_9StringCmpESaIS8_E"
	    + "E16_M_insert_uniqueERKS8_";
	d = new GxxV3Demangler(s);
	e = "std::_Rb_tree<char const *, "
	    + "std::pair<char const * const, "
	    + "google::(anonymous namespace)::CommandLineFlag *>, "
	    + "std::_Select1st<std::pair<char const * const, "
	    + "google::(anonymous namespace)::CommandLineFlag *>>, "
	    + "google::(anonymous namespace)::StringCmp, "
	    + "std::allocator<std::pair<char const * const, "
	    + "google::(anonymous namespace)::CommandLineFlag *>>>"
	    + "::_M_insert_unique";
	test(e, d.getName());
    }
}
